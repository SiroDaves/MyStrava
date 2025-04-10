package com.siro.mystrava.core.utils

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

fun String.getDateTime(): LocalDateTime {
    val dtf = DateTimeFormatter.ISO_DATE_TIME
    val zdt: ZonedDateTime =
        ZonedDateTime.parse(this, dtf)
    return zdt.toLocalDateTime()
}

//Calculates pace from the moving time with the distance as an arguement
fun Int.getPaceFromMovingTime(distance: Float): String {
    val secondsPerMile = this / (distance / 1609)
    return secondsPerMile.toInt().getTimeString()
}

//Returns time based on seconds passed in
fun Int.getTimeString(): String {
    return "${(this / 60)}:${(this % 60)}"
}

//Returns time based on seconds passed in
fun Int.getTimeFloat(): Float {
    return "${(this / 60)}.${((this % 60))}".toFloat()
}

fun LocalTime.get12HrTime(): String {
    val pattern = "hh:mm a"
    return this.format(DateTimeFormatter.ofPattern(pattern))
}

fun LocalDate.getDateString(): String {
    return this.month.name.fixCase() + " " + this.dayOfMonth
}

//Returns time based on seconds passed in
fun Int.getTimeStringHoursAndMinutes(): String {
    val hours = this / 3600
    return if (hours == 0) {
        "${((this % 3600) / 60)}m"
    } else if (hours > 24) {
        val days = hours / 24
        val remainderHours = hours - (days * 24)
        "${days}d ${remainderHours}h"
    } else
        "${hours}h ${((this % 3600) / 60)}m"
}

fun String.getDate(): LocalDate {
    val dtf = DateTimeFormatter.ISO_DATE_TIME
    val zdt: ZonedDateTime =
        ZonedDateTime.parse(this, dtf)
    val localDateTime = zdt.toLocalDateTime()
    return localDateTime.toLocalDate()
}

fun String.getTime(): LocalTime {
    val dtf = DateTimeFormatter.ISO_DATE_TIME
    val zdt: ZonedDateTime =
        ZonedDateTime.parse(this, dtf)
    val localDateTime = zdt.toLocalDateTime()
    return localDateTime.toLocalTime()
}

fun formatElapsedTime(timeInSeconds: Int): String {
    val hours = timeInSeconds / 3600
    val minutes = (timeInSeconds % 3600) / 60
    val seconds = timeInSeconds % 60

    return when {
        hours > 0 -> {
            if (minutes > 0) "${hours}hr ${minutes}min" else "${hours}hr"
        }
        minutes > 0 -> {
            if (seconds > 0) "${minutes}min ${seconds}sec" else "${minutes}min"
        }
        else -> "${seconds}sec"
    }
}