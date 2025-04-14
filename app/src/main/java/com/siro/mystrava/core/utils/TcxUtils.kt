package com.siro.mystrava.core.utils

import android.annotation.SuppressLint
import android.content.*
import android.os.*
import android.provider.MediaStore
import com.siro.mystrava.data.models.activites.*
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@SuppressLint("NewApi")
fun generateTcxFromStream(
    context: Context,
    activity: ActivityItem,
    stream: List<StreamItem>,
    activityStartTime: ZonedDateTime = ZonedDateTime.now()
): Boolean {
    val streamMap = stream.associateBy { it.type }

    val latlng = streamMap["latlng"]?.data?.map { it as List<Double> } ?: emptyList()
    val altitude = streamMap["altitude"]?.data
    val distance = streamMap["distance"]?.data
    val heartrate = streamMap["heartrate"]?.data

    val startTime = activityStartTime
    val formatter = DateTimeFormatter.ISO_INSTANT
    val filename = "${activity.name}.tcx"

    val tcx = StringBuilder().apply {
        append("""<?xml version="1.0" encoding="UTF-8"?>""").append("\n")
        append("""<TrainingCenterDatabase xmlns="http://www.garmin.com/xmlschemas/TrainingCenterDatabase/v2" """)
        append("""xmlns:ns2="http://www.garmin.com/xmlschemas/UserProfile/v2" """)
        append("""xmlns:ns3="http://www.garmin.com/xmlschemas/ActivityExtension/v2" """)
        append("""xmlns:ns5="http://www.garmin.com/xmlschemas/ActivityGoals/v1" """)
        append("""xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" """)
        append("""xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" """)
        append("""xsi:schemaLocation="http://www.garmin.com/xmlschemas/TrainingCenterDatabase/v2 http://www.garmin.com/xmlschemas/TrainingCenterDatabasev2.xsd">""").append("\n")
        append("<Activities>\n")
        append("""<Activity Sport="${activity.type}">""").append("\n\t")
        append("<Id>${startTime.format(formatter)}</Id>\n\t\t")
        append("<Creator>\n\t\t\t")
        append("<Name>Xiaomi Watch 5</Name>\n\t\t")
        append("</Creator>\n\t\t")
        append("<Lap StartTime=\"${startTime.format(formatter)}\">\n\t\t\t")
        append("<DistanceMeters>${activity.distance}</DistanceMeters>\n\t\t\t\t")
        append("<TotalTimeSeconds>${activity.moving_time}</TotalTimeSeconds>\n\t\t\t\t")
        append("<Calories>${activity.calories}</Calories>\n\t\t\t\t")
        append("<AverageHeartRateBpm>\n\t\t\t\t")
        append("<Value>${activity.average_heartrate}</Value>\n\t\t\t\t\t")
        append("</AverageHeartRateBpm>\n\t\t\t\t")
        append("<Extensions>\n\t\t\t\t\t")
        append("<ns3:LX>\n\t\t\t\t\t")
        append("<ns3:AvgSpeed>${activity.average_speed}</ns3:AvgSpeed>\n\t\t\t\t")
        append("</ns3:LX>\n\t\t\t\t\t\t")
        append("</Extensions>\n\t\t\t\t")
        append("<Track>\n")

        val pointCount = latlng.size
        for (i in 0 until pointCount) {
            val timestamp = startTime.plusSeconds(i.toLong()).format(formatter)
            val coords = latlng[i]
            val lat = coords.getOrNull(0)
            val lng = coords.getOrNull(1)

            append("        <Trackpoint>\n")
            append("          <Time>$timestamp</Time>\n")

            if (lat != null && lng != null) {
                append("          <Position>\n")
                append("            <LatitudeDegrees>$lat</LatitudeDegrees>\n")
                append("            <LongitudeDegrees>$lng</LongitudeDegrees>\n")
                append("          </Position>\n")
            }

            altitude?.getOrNull(i)?.let {
                append("          <AltitudeMeters>$it</AltitudeMeters>\n")
            }

            distance?.getOrNull(i)?.let {
                append("          <DistanceMeters>$it</DistanceMeters>\n")
            }

            heartrate?.getOrNull(i)?.let {
                append("          <HeartRateBpm>\n")
                append("            <Value>${it.toInt()}</Value>\n")
                append("          </HeartRateBpm>\n")
            }

            append("        </Trackpoint>\n")
        }

        append("\t\t\t\t</Track>\n\t\t\t")
        append("</Lap>\n\t\t")
        append("</Activity>\n\t")
        append("</Activities>\n")
        append("</TrainingCenterDatabase>\n")
    }.toString()

    try {
        val resolver = context.contentResolver
        val values = ContentValues().apply {
            put(MediaStore.Downloads.DISPLAY_NAME, filename)
            //put(MediaStore.Downloads.MIME_TYPE, "application/xml")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                put(MediaStore.Downloads.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
            }
        }

        val uri = resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, values)
            ?: return false

        resolver.openOutputStream(uri)?.use { streamOut ->
            streamOut.write(tcx.toByteArray())
        }

        return true
    } catch (e: Exception) {
        e.printStackTrace()
        return false
    }
}

