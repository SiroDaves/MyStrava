package com.siro.mystrava.data.models.fit

import java.time.Duration
import java.util.Date
import kotlin.math.max
import kotlin.times

class SportContainer(val summary: SportSummary, val sportDetail: SportDetail) {

    fun Date.asUnixTimestamp(): Long = time / 1000

    val activityType: ActivityType = ActivityType.fromZepp(summary.type)

    val id: String = summary.trackid

    val startDate: Date = Date(sportDetail.trackid * 1000L)

    val endDate: Date
    val activityDuration: Duration
        get() = Duration.ofMillis(endDate.time - startDate.time)

    val timedData = ArrayList<ActivityData>()

    init {

        val zeppCoordinateFactor = 100000000.0
        //To initialize all data. We need to find the correct endDate based either on time accumulation or the endDate of the sport summary
        val maxEndTimeRecord: Long = startDate.asUnixTimestamp() + timeList.sum().toLong()
        endDate = Date(max(summary.end_time.toLong() * 1000, maxEndTimeRecord * 1000))

        //Collect metric and timed data for each metric !
        val times = timeList
        val latitudes = latitudeList
        val longitudes = longitudeList
        val altitudes = altList
        val timedHr = timedHeartRate(startDate.asUnixTimestamp(), endDate.asUnixTimestamp())
        val timeSpeed = timedSpeed(startDate.asUnixTimestamp(), endDate.asUnixTimestamp())
        val timeGait = timedGait(startDate.asUnixTimestamp(), endDate.asUnixTimestamp())
        //Now create timedData based on time values !

        var unixTs = startDate.asUnixTimestamp()
        var latitude: Long? = if (latitudes.isNotEmpty()) 0 else null
        var longitude: Long? = if (longitudes.isNotEmpty()) 0 else null

        times.forEachIndexed { index, i ->
            unixTs += i
            var latitudeInDeg: Double? = null
            var longitudeInDeg: Double? = null
            if (latitude != null) {
                latitude += latitudes[index]
                latitudeInDeg = latitude / zeppCoordinateFactor
            }
            if (longitude != null) {
                longitude += longitudes[index]
                longitudeInDeg = longitude / zeppCoordinateFactor
            }
            val gait: GaitContainer = timeGait[unixTs]!!

            timedData.add(
                ActivityData(
                    Date(unixTs * 1000),
                    latitudeInDeg,
                    longitudeInDeg,
                    altitudes.getOrNull(index),
                    timedHr[unixTs]!!,
                    timeSpeed[unixTs]!!,
                    gait.step,
                    gait.stride,
                    gait.stepFrequency
                )
            )
        }
    }

    private val timeList: List<Int>
        get() = sportDetail.time?.split(';')?.filter { it.isNotEmpty() }?.map { it.toInt() } ?: listOf()

    private val latitudeList: List<Long>
        get() = sportDetail.longitude_latitude?.split(';')?.filter { it.isNotEmpty() }?.map { it.split(',')[0].toLong() } ?: listOf()

    private val longitudeList: List<Long>
        get() = sportDetail.longitude_latitude?.split(';')?.filter { it.isNotEmpty() }?.map { it.split(',')[1].toLong() } ?: listOf()

    private val altList: List<Long>
        get() {
            val noZeppValue = -2000000L
            val tmp: MutableList<Long> = sportDetail.altitude?.split(';')?.filter { it.isNotEmpty() }?.map { it.toLong() }?.toMutableList() ?: java.util.ArrayList()
            //Manage possible -20000 at the beginning and replace with the first real alt value
            val idx = tmp.lastIndexOf(noZeppValue)
            //Replace if possible and not out of bound
            if (idx != -1 && (idx + 1) < tmp.size) {
                for (i in 0..idx) {
                    tmp[i] = tmp[idx + 1]
                }
            }
            return tmp;
        }

    private fun timedHeartRate(from: Long, to: Long): Map<Long, Short> {
        val elements = sportDetail.heart_rate?.split(';')?.filter { it.isNotEmpty() }?.map {
            var (timeDelta, hrDelta) = it.split(',');
            if (timeDelta.isEmpty()) {
                timeDelta = "1"
            }
            Pair(timeDelta.toLong(), hrDelta.toShort())
        } ?: listOf()
        return timedCumulative(from, to, elements, 0) { s1, s2 ->
            (s1 + s2).toShort()
        }
    }

    private fun timedSpeed(from: Long, to: Long): Map<Long, Float> {
        val elements = sportDetail.speed?.split(';')?.filter { it.isNotEmpty() }?.map { val (timeDelta, speed) = it.split(','); Pair(timeDelta.toLong(), speed.toFloat()) } ?: listOf()
        return timedFixed(from, to, elements, 0f)
    }

    private fun timedGait(from: Long, to: Long): Map<Long, GaitContainer> {

        val pairOfStep = ArrayList<Pair<Long, Long>>()
        val pairOfStride = ArrayList<Pair<Long, Long>>()
        val pairOfStepFreq = ArrayList<Pair<Long, Long>>()

        sportDetail.gait?.split(';')?.filter { it.isNotEmpty() }?.forEach {
            val (timeDelta, stepNumber, stride, stepFrequency) = it.split(',');

            pairOfStep.add(Pair(timeDelta.toLong(), stepNumber.toLong()))
            pairOfStride.add(Pair(timeDelta.toLong(), stride.toLong()))
            pairOfStepFreq.add(Pair(timeDelta.toLong(), stepFrequency.toLong()))
        }

        val timedStep = timedCumulative(from, to, pairOfStep, 0L) { l1, l2 -> l1 + l2 }
        val timedStride = timedFixed(from, to, pairOfStride, 0L)
        val timedFreq = timedFixed(from, to, pairOfStepFreq, 0L)

        return timedStep.keys.associateWith { GaitContainer(timedStep[it]!!, timedStride[it]!!, timedFreq[it]!!) }

    }

    private fun <T : Number> timedCumulative(from: Long, to: Long, elements: List<Pair<Long, T>>, initValue: T, operator: (T, T) -> T): java.util.HashMap<Long, T> {
        val result = HashMap<Long, T>()
        var workingDate = from
        var value: T = initValue

        elements.forEachIndexed { index, pair ->
            value = operator(value, pair.second)
            val start = if (index == 0) 0 else 1
            for (i in start..pair.first) {
                result[workingDate++] = value
            }
        }
        //Fill to the end
        while (workingDate <= to) {
            result[workingDate++] = value
        }
        return result
    }

    private fun <T : Number> timedFixed(from: Long, to: Long, elements: List<Pair<Long, T>>, initialValue: T): java.util.HashMap<Long, T> {
        val result = HashMap<Long, T>()
        var workingDate = from
        var value: T = initialValue
        elements.forEachIndexed { index, pair ->
            value = pair.second
            val start = if (index == 0) 0 else 1
            for (i in start..pair.first) {
                result[workingDate++] = value
            }
        }
        //Fill to the end
        while (workingDate <= to) {
            result[workingDate++] = value
        }
        return result
    }

}

class GaitContainer(val step: Long, val stride: Long, val stepFrequency: Long)

class ActivityData(
    val timestamp: Date, val latitude: Double?, val longitude: Double?, val altitude: Long?,
    val heartRate: Short?, val speed: Float?, val step: Long?, val stride: Long?, val stepFrequency: Long?
) {

}