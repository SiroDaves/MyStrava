package com.siro.mystrava.core.utils

import android.util.Log
import java.time.*
import java.util.*

object DateUtils {
    private val now: LocalDate = LocalDate.now()
    private val yearMonth: YearMonth = YearMonth.from(now)

    val currentDateTime: LocalDate = now
    val currentMonthInt: Int = now.monthValue
    val currentDayInt: Int = now.dayOfMonth
    val currentYearInt: Int = now.year
    val currentDayOfYear: Int = now.dayOfYear

    val currentMonth: Pair<Int, String> = getEpoch(now.year, now.monthValue - 1, 1)
    val previousMonth: Pair<Int, String> = getEpoch(now.year, now.monthValue - 2, 1)
    val twoMonthsAgo: Pair<Int, String> = getEpoch(now.year, now.monthValue - 3, 1)

    val currentYear: Pair<Int, String> =
        getEpoch(now.year, now.monthValue - 1, now.dayOfMonth).first to now.year.toString()
    val prevYear: Pair<Int, String> =
        getEpoch(now.year - 1, 0, 1).first to (now.year - 1).toString()
    val twoYearsAgo: Pair<Int, String> =
        getEpoch(now.year - 2, 0, 1).first to (now.year - 2).toString()

    var currentWeek: MutableList<Pair<Int, Int>> = mutableListOf()

    private const val PREVIOUS_WEEK = -1
    private const val TWO_WEEKS_AGO = -2

    val monthWeekMap: MutableMap<Int, MutableList<Pair<Int, Int>>> = generateMonthBreakdown()

    private fun generateMonthBreakdown(): MutableMap<Int, MutableList<Pair<Int, Int>>> {
        val result = mutableMapOf<Int, MutableList<Pair<Int, Int>>>()
        val firstDayOfMonth = yearMonth.atDay(1)
        val monthLength = yearMonth.lengthOfMonth()
        val startDayOffset = firstDayOfMonth.dayOfWeek.value % 7 // Sunday=0, Saturday=6

        val previousMonth = yearMonth.minusMonths(1)
        val previousMonthLength = previousMonth.lengthOfMonth()

        var dayCounter = 1 - startDayOffset

        var weekIndex = 0
        while (dayCounter <= monthLength) {
            val week = mutableListOf<Pair<Int, Int>>()
            for (i in 0 until 7) {
                val date = when {
                    dayCounter < 1 -> {
                        // Previous month
                        val day = previousMonthLength + dayCounter
                        previousMonth.monthValue to day
                    }
                    dayCounter > monthLength -> {
                        // Next month (not needed here)
                        continue
                    }
                    else -> {
                        yearMonth.monthValue to dayCounter
                    }
                }

                if (date.second == currentDayInt && date.first == currentMonthInt) {
                    currentWeek = week
                }

                if (dayCounter in 1..monthLength) {
                    week.add(date)
                }

                dayCounter++
            }

            result[weekIndex++] = week
        }

        result[PREVIOUS_WEEK] = getPreviousWeek(-1)
        result[TWO_WEEKS_AGO] = getPreviousWeek(-2)

        Log.d("DateUtils", "Current Week: $currentWeek")
        Log.d("DateUtils", "Month Breakdown: $result")

        return result
    }

    private fun getPreviousWeek(offset: Int): MutableList<Pair<Int, Int>> {
        val weekList = mutableListOf<Pair<Int, Int>>()
        val calendar = Calendar.getInstance()
        calendar.time = Date()
        calendar.add(Calendar.WEEK_OF_YEAR, offset)

        for (i in 0 until 7) {
            val month = calendar.get(Calendar.MONTH) + 1
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            weekList.add(month to day)
            calendar.add(Calendar.DAY_OF_MONTH, 1)
        }

        return weekList
    }
}

fun getEpoch(year: Int, month: Int, day: Int, hour: Int = 0, minute: Int = 0): Pair<Int, String> {
    val calendar: Calendar = Calendar.getInstance()
    calendar.set(year, month, day, hour, minute)
    return calendar.toInstant().epochSecond.toInt() to
            calendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault())
}
