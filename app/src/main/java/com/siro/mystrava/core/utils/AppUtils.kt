package com.siro.mystrava.core.utils

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.net.Uri
import android.provider.OpenableColumns
import androidx.compose.ui.unit.dp
import com.siro.mystrava.presentation.viewmodels.UnitType
import java.time.*

sealed class Food(val foodName: String) {
    class MacAndCheese() : Food("Mac and Cheese")

}


fun doSomethign(foodName: Food) {
    var boolean: Boolean

    when (foodName.foodName) {

    }
}

fun callIt() {
    doSomethign(Food.MacAndCheese())
}


//Gets bar graph height based on miles
fun Int.getBarHeight() = when (this.div(1609)) {
    in 1..2 -> {
        20.dp
    }

    in 2..5 -> {
        50.dp
    }

    in 5..8 -> {
        65.dp
    }

    in 8..100 -> {
        90.dp
    }

    else -> {
        0.dp
    }
}

fun Float.getMiles(): Double = (this * 0.000621371192).round(2);

fun Double.round(decimals: Int = 2): Double = "%.${decimals}f".format(this).toDouble()

fun String.fixCase(): String = this.toLowerCase().capitalize()

fun getDateTimeDisplayString(date: LocalDate, time: LocalTime): String =
    "${date.dayOfWeek.name.fixCase()}, ${date.month.name.fixCase()} ${date.dayOfMonth}, ${date.year} at ${time}"

fun Int.poundsToKg(): Int = (this / 2.205).toInt()

fun caloriesBurned(met: Float, weight: Int, minutesWorkedOut: Int): Int {
    val caloriesPerMinute = (met * 3.5 * weight.poundsToKg()) / 200
    return (caloriesPerMinute * minutesWorkedOut).toInt()
}

fun Float.getElevationString(selectedUnitType: UnitType): String {
    when (selectedUnitType) {
        UnitType.Imperial -> {
            var elevation: Number = (this * 3.281).round(1)
            var elevationMeasurement = "ft"

            if (elevation.toInt() > 1500) {
                elevation = (this * 1.094).round(1)
                elevationMeasurement = "yd"
            }

            if (elevation.toInt() > 1000) {
                elevation = (this / 1609).toDouble().round(1)
                elevationMeasurement = "mi"
            }

            return "${
                elevation.toString().replace("0*$".toRegex(), "").replace("\\.$".toRegex(), "")
            } $elevationMeasurement"
        }

        UnitType.Metric -> {
            return "${
                this.toDouble().round(1).toString().replace("0*$".toRegex(), "")
                    .replace("\\.$".toRegex(), "")
            } m"
        }
    }
}

fun Float.getDistanceMiles(selectedUnitType: UnitType): Double {
    return when (selectedUnitType) {
        UnitType.Imperial -> {
            this.div(1609).toDouble().round(2)
        }

        UnitType.Metric -> {
            (this / 1000).toDouble().round(2)
        }
    }
}

fun Float.getDistanceString(selectedUnitType: UnitType, isYearSummary: Boolean = false): String {
    val decimals = if (isYearSummary) 0 else 1

    return when (selectedUnitType) {
        UnitType.Imperial -> {
            "${
                this.div(1609).toDouble().round(decimals).toString().replace("0*$".toRegex(), "")
                    .replace("\\.$".toRegex(), "")
            } mi"
        }

        UnitType.Metric -> {
            var elevation: Number = (this / 1000).toDouble().round(decimals)
            "${elevation.toString().replace("0*$".toRegex(), "").replace("\\.$".toRegex(), "")} km"
        }
    }
}

fun Int.format(): String {
    return if (this in 0..9) "0$this" else this.toString()
}

fun getAveragePaceString(distance: Float, time: Int, selectedUnitType: UnitType): String {
    when (selectedUnitType) {
        UnitType.Imperial -> {
            val distanceInMiles = distance.div(1609).toDouble().round(2)
            val minutes = time.div(60)

            val pace = minutes.div(distanceInMiles).round(2)

            val remainder = (pace - pace.toInt())
            val secondsPace = remainder.times(60).toInt()

            return "${pace.toInt()}:${secondsPace.format()} / mi"
        }

        UnitType.Metric -> {
            val distanceInMeters = distance.div(1000).toDouble().round(2)
            val minutes = time.div(60)

            val pace = minutes.div(distanceInMeters).round(2)

            val remainder = (pace - pace.toInt())
            val secondsPace = remainder.times(60).toInt()

            return "${pace.toInt()}:${secondsPace.format()} / km"
        }
    }
}

fun Float.getAveragePaceFromDistance(time: Int): Double {
    val distanceInMiles = this.div(1609).toDouble().round(2)
    val minutes = time.div(60)

    return minutes.div(distanceInMiles).round(2)
}

@SuppressLint("DefaultLocale")
fun calculatePace(movingTime: Int, distance: Float): String {
    if (distance <= 0) return "--:-- /km"

    val totalMinutes = movingTime / 60.0
    val paceMinutesPerKm = totalMinutes / distance

    val minutes = paceMinutesPerKm.toInt()
    val seconds = ((paceMinutesPerKm - minutes) * 60).toInt()

    return "$minutes:${String.format("%02d", seconds)} /km"
}

fun ContentResolver.getFileSize(uri: Uri): Long {
    var size = 0L
    val returnCursor = query(uri, null, null, null, null)
    if (returnCursor != null) {
        val sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE)
        returnCursor.moveToFirst()
        size = returnCursor.getLong(sizeIndex)
        returnCursor.close()
    }
    return size
}

fun ContentResolver.getFormattedFileSize(uri: Uri): String {
    val size = this.getFileSize(uri)
    return when {
        size >= 1024 * 1024 * 1024 -> {
            val value = size / (1024.0 * 1024.0 * 1024.0)
            if (value % 1 == 0.0) "%.0f GB" else "%.1f GB".format(value)
        }
        size >= 1024 * 1024 -> {
            val value = size / (1024.0 * 1024.0)
            if (value % 1 == 0.0) "%.0f MB" else "%.1f MB".format(value)
        }
        size >= 1024 -> {
            val value = size / 1024.0
            if (value % 1 == 0.0) "%.0f KB" else "%.1f KB".format(value)
        }
        else -> "$size bytes"
    }
}

fun ContentResolver.getFileName(uri: Uri): String {
    var name = ""
    val returnCursor = this.query(uri, null, null, null, null)
    returnCursor?.use {
        if (it.moveToFirst()) {
            val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            if (nameIndex != -1) {
                name = it.getString(nameIndex)
            } else {
                // Fallback to Uri path if display name not available
                name = uri.path?.substringAfterLast('/') ?: "unnamed_file"
            }
        }
    }
    return name
}