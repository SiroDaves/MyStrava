package com.siro.mystrava.core.utils

import androidx.lifecycle.*
import com.garmin.fit.*
import kotlinx.coroutines.*
import java.io.*
import java.io.File
import java.util.Date

class FitConverter {
    private val log = LoggerFactory.getLogger(com.github.h3llk33p3r.service.FitConverter::class.java)

    fun convertToFit(outputDirectory: File, summary: SportSummary, detail: SportDetail): File? {
        val container = SportContainer(summary, detail)
        log.info("Generating fit file for activity [{}]", container.id)
        if (container.activityType.supported) {

            val messages: MutableList<Mesg> = ArrayList()
            //Mandatory field
            messages.add(generateFileId(container.startDate))
            messages.add(generateDeviceInfo(container.startDate))
            messages.add(generateActivityMessage(container))
            //Manage data of activity
            messages.addAll(fillActivity(container))
            val outputFile = File(outputDirectory, "${container.activityType.name}-${container.id}.fit")
            writeFitFile(outputFile, messages)
            return outputFile
        } else {
            log.warn("Unsupported activity type identified in Zepp with [{}]. Skipping trackId [{}]", container.summary.type, container.id)
        }
        return null

    }

    private fun fillActivity(sport: SportContainer): Collection<Mesg> {

        val messages: MutableList<Mesg> = ArrayList()
        //First we declare the  start of the activity
        val start = EventMesg()
        start.timestamp = DateTime(sport.startDate)
        start.event = Event.TIMER
        start.eventType = EventType.START
        messages.add(start)

        when (sport.activityType) {
            ActivityType.RUNNING -> fillOutdoorWithGpsRecord(sport, messages)
            ActivityType.INDOOR_SWIMMING -> fillSwimming(sport, messages)
            ActivityType.WALKING -> fillOutdoorWithGpsRecord(sport, messages)
            ActivityType.CYCLING -> fillOutdoorWithGpsRecord(sport, messages)
            UNKNOWN -> TODO()
        }

        val end = EventMesg()
        end.timestamp = DateTime(sport.endDate)
        end.event = Event.TIMER
        end.eventType = EventType.STOP_ALL
        messages.add(end)
        return messages
    }

    private fun fillSwimming(sport: SportContainer, messages: MutableList<Mesg>) {
        val lap = LapMesg()
        lap.timestamp = DateTime(sport.startDate)
        lap.totalElapsedTime = sport.activityDuration.toSeconds().toFloat()
        lap.messageIndex = 0
        lap.event = Event.LAP
        lap.startTime = DateTime(sport.startDate)
        lap.totalTimerTime = sport.activityDuration.toSeconds().toFloat()

        sport.summary.dis?.let { lap.totalDistance = it.toFloat() }
        sport.summary.altitude_ascend?.let { lap.totalAscent = it }
        sport.summary.altitude_descend?.let { lap.totalDescent = it }
        messages.add(lap)

        val session = sessionMesg(sport)
        session.sport = sport.activityType.fitType
        messages.add(session)


    }

    /**
     * Method to manage a running activity on outdoor with GPS coordinate like :
     *
     * Cycling, running, walking...
     */
    private fun fillOutdoorWithGpsRecord(sport: SportContainer, messages: MutableList<Mesg>) {

        for (data in sport.timedData) {
            //Compute new data point
            val recordMesg = RecordMesg()
            recordMesg.timestamp = DateTime(data.timestamp)
            recordMesg.heartRate = data.heartRate
            //RPM => Revolution per minute. We must divide per 2 the number of step.
            data.speed?.let { recordMesg.speed = it }
            //Convert as mention in the rest doc !
            data.altitude?.let { recordMesg.altitude = (it / 100).toFloat() }
            data.latitude?.let { recordMesg.positionLat = degreesToSemicircles(it) }
            data.longitude?.let { recordMesg.positionLong = degreesToSemicircles(it) }
            if (sport.activityType.gaitSupported) {
                data.stepFrequency?.let { recordMesg.cadence = (data.stepFrequency / 2).toShort() }
                data.stride?.let { recordMesg.stepLength = (it * 10).toFloat() }
            }
            messages.add(recordMesg)
        }

        //FIXME: Only manage single lap for now !
        val lap = LapMesg()
        lap.timestamp = DateTime(sport.startDate)
        lap.totalElapsedTime = sport.activityDuration.toSeconds().toFloat()
        lap.messageIndex = 0
        lap.event = Event.LAP
        lap.startTime = DateTime(sport.startDate)
        lap.totalTimerTime = sport.activityDuration.toSeconds().toFloat()

        sport.summary.dis?.let { lap.totalDistance = it.toFloat() }
        sport.summary.altitude_ascend?.let { lap.totalAscent = it }
        sport.summary.altitude_descend?.let { lap.totalDescent = it }
        messages.add(lap)

        val session = sessionMesg(sport)
        session.sport = sport.activityType.fitType
        messages.add(session)

    }

    private fun sessionMesg(sport: SportContainer): SessionMesg {
        val session = SessionMesg()
        session.timestamp = DateTime(sport.startDate)
        session.startTime = DateTime(sport.startDate)
        session.totalElapsedTime = sport.activityDuration.toSeconds().toFloat()
        session.totalTimerTime = sport.activityDuration.toSeconds().toFloat()
        session.messageIndex = 0

        //FIXME: Only manage single lap for now !
        session.firstLapIndex = 0
        session.numLaps = 1

        sport.summary.altitude_ascend?.let { if (it > 0) session.totalAscent = it }
        sport.summary.altitude_descend?.let { if (it > 0) session.totalDescent = it }
        sport.summary.dis?.let { session.totalDistance = it.toFloat() }
        sport.summary.calorie?.let { session.totalCalories = it.toFloat().toInt() }
        sport.summary.avg_heart_rate?.let { if (it.toFloat() > 0) session.avgHeartRate = it.toFloat().toInt().toShort() }
        sport.summary.min_heart_rate?.let { if (it.toFloat() > 0) session.minHeartRate = it.toShort() }
        sport.summary.max_heart_rate?.let { if (it.toFloat() > 0) session.maxHeartRate = it.toShort() }
        sport.summary.avg_stride_length?.let { if (it > 0) session.avgStepLength = it.toFloat() * 10 }

        if (sport.activityType == INDOOR_SWIMMING) {
            sport.summary.swim_pool_length?.let {
                if (it > 0) {
                    session.poolLength = it.toFloat()
                    session.poolLengthUnit = DisplayMeasure.METRIC
                }
            }
            // We are not using the avg_stroke_speed because we do not manage pause here!
            // We prefer to let garmin compute to have a better value
            sport.summary.total_trips?.let { if (it > 0) session.numActiveLengths = it }
            sport.summary.total_strokes?.let { if (it > 0) session.totalStrokes = it.toLong() }
            sport.summary.avg_distance_per_stroke?.let { if (it > 0) session.avgStrokeDistance = it / 100 } //mm -> m
            sport.summary.max_stroke_speed?.let { if (it > 0) session.enhancedMaxSpeed = it }
            sport.summary.swim_style?.let { if (it > 0) session.swimStroke = swimStroke(it) }
        }

        //Not setting avg cadence => Garmin connect compute it !
        return session
    }


    private fun writeFitFile(outputFile: File, messages: List<Mesg>) {
        val encode: FileEncoder
        try {
            encode = FileEncoder(outputFile, Fit.ProtocolVersion.V2_0)
        } catch (e: FitRuntimeException) {
            log.error("Error opening file [{}]", outputFile, e)
            return
        }
        messages.forEach { encode.write(it) }
        // Close the output stream
        try {
            encode.close()
        } catch (e: FitRuntimeException) {
            log.error("Error closing encode for file [{}]", outputFile, e)
            return
        }
        log.info("Fit file created [{}]", outputFile.name)
    }

    private fun generateActivityMessage(summary: SportContainer): ActivityMesg {
        val msg = ActivityMesg()
        //Field set with zepp application fit export
        //FIXME : Must be on Z timezone ?
        msg.timestamp = DateTime(summary.startDate)
        msg.totalTimerTime = summary.activityDuration.toSeconds().toFloat()
        //FIXME: Alway 1 session event if multisport ?
        msg.numSessions = 1
        msg.type = Activity.MANUAL
        msg.localTimestamp = DateTime(summary.startDate).timestamp
        return msg
    }

    private fun generateFileId(timestamp: Date): Mesg {
        val fileIdMesg = FileIdMesg()
        fileIdMesg.type = com.garmin.fit.File.ACTIVITY
        fileIdMesg.manufacturer = Manufacturer.DEVELOPMENT
        fileIdMesg.product = FIT_CONVERTER_PRODUCT
        fileIdMesg.timeCreated = DateTime(timestamp)
        fileIdMesg.serialNumber = FIT_CONVERTER_SN
        return fileIdMesg
    }

    private fun generateDeviceInfo(timestamp: Date): Mesg {
        val deviceInfoMesg = DeviceInfoMesg()
        deviceInfoMesg.deviceIndex = DeviceIndex.CREATOR
        deviceInfoMesg.manufacturer = Manufacturer.DEVELOPMENT
        deviceInfoMesg.product = FIT_CONVERTER_PRODUCT
        deviceInfoMesg.productName = PRODUCT_NAME
        deviceInfoMesg.serialNumber = FIT_CONVERTER_SN
        deviceInfoMesg.softwareVersion = SOFTWARE_VERSION
        deviceInfoMesg.timestamp = DateTime(timestamp)
        return deviceInfoMesg
    }

    private fun swimStroke(zeppValue: Int): SwimStroke {
        return when (zeppValue) {
            1 -> SwimStroke.BREASTSTROKE
            2 -> SwimStroke.FREESTYLE
            4 -> SwimStroke.MIXED
            else -> SwimStroke.INVALID
        }
    }

    companion object {
        private const val PRODUCT_NAME = "MyStrava"
        private const val FIT_CONVERTER_PRODUCT = 1
        private const val FIT_CONVERTER_SN: Long = 9131870
        private const val SOFTWARE_VERSION = 1.0f
    }
}