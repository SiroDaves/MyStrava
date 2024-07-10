package com.siro.mystrava.core.utils

import com.garmin.fit.*
import java.io.File
import java.io.FileInputStream
import android.util.Log

class FitParser {
    data class FitActivityData(
        val timestamp: DateTime? = null,
        val totalTimerTime: Float? = null,
        val numSessions: Int? = null
    )

    data class FitSessionData(
        val sport: Sport? = null,
        val startTime: DateTime? = null,
        val totalDistance: Float? = null,
        val totalElapsedTime: Float? = null,
        val totalTimerTime: Float? = null,
        val avgSpeed: Float? = null,
        val maxSpeed: Float? = null,
        val avgHeartRate: Short? = null,
        val maxHeartRate: Short? = null
    )

    data class FitRecordPoint(
        val timestamp: DateTime? = null,
        val latitude: Int? = null,
        val longitude: Int? = null,
        val altitude: Float? = null,
        val heartRate: Short? = null,
        val speed: Float? = null,
        val cadence: Short? = null,
        val power: Int? = null,
        val temperature: Byte? = null
    )

    data class FitFileData(
        var activity: FitActivityData? = null,
        var session: FitSessionData? = null,
        val records: MutableList<FitRecordPoint> = mutableListOf()
    )

    fun parseFitFile(fitFile: File): FitFileData? {
        try {
            val decode = Decode()
            val mesgBroadcaster = MesgBroadcaster(decode)
            val fitData = FitFileData()

            // Add activity listener using the proper method
            mesgBroadcaster.addListener(object : ActivityMesgListener {
                override fun onMesg(mesg: ActivityMesg) {
                    fitData.activity = FitActivityData(
                        timestamp = mesg.timestamp,
                        totalTimerTime = mesg.totalTimerTime,
                        numSessions = mesg.numSessions
                    )
                    Log.d("FitParser", "Activity: timestamp=${mesg.timestamp}")
                }
            })

            // Add session listener
            mesgBroadcaster.addListener(object : SessionMesgListener {
                override fun onMesg(mesg: SessionMesg) {
                    fitData.session = FitSessionData(
                        sport = mesg.sport,
                        startTime = mesg.startTime,
                        totalDistance = mesg.totalDistance,
                        totalElapsedTime = mesg.totalElapsedTime,
                        totalTimerTime = mesg.totalTimerTime,
                        avgSpeed = mesg.avgSpeed,
                        maxSpeed = mesg.maxSpeed,
                        avgHeartRate = mesg.avgHeartRate,
                        maxHeartRate = mesg.maxHeartRate
                    )
                    Log.d("FitParser", "Session: sport=${mesg.sport}, distance=${mesg.totalDistance}")
                }
            })

            // Add record listener for data points
            mesgBroadcaster.addListener(object : RecordMesgListener {
                override fun onMesg(mesg: RecordMesg) {
                    val recordPoint = FitRecordPoint(
                        timestamp = mesg.timestamp,
                        latitude = mesg.positionLat,
                        longitude = mesg.positionLong,
                        altitude = mesg.altitude,
                        heartRate = mesg.heartRate,
                        speed = mesg.speed,
                        cadence = mesg.cadence,
                        power = mesg.power,
                        temperature = mesg.temperature
                    )
                    fitData.records.add(recordPoint)

                    // Log only occasionally to avoid spamming the log
                    if (fitData.records.size % 100 == 0) {
                        Log.d("FitParser", "Records: ${fitData.records.size}")
                    }
                }
            })

            // Check file integrity first
            val inputFileStream = FileInputStream(fitFile)
            val isFileValid = decode.checkFileIntegrity(inputFileStream)
            inputFileStream.close()

            if (!isFileValid) {
                Log.e("FitParser", "FIT file integrity check failed")
                return null
            }

            // Now decode the file
            val decodeFileStream = FileInputStream(fitFile)
            mesgBroadcaster.run(decodeFileStream)
            decodeFileStream.close()

            Log.d("FitParser", "Parsing complete: ${fitData.records.size} data points")
            return fitData

        } catch (e: Exception) {
            Log.e("FitParser", "Error parsing FIT file: ${e.message}")
            e.printStackTrace()
            return null
        }
    }
}