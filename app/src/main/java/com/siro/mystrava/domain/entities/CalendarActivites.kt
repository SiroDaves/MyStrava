package com.siro.mystrava.domain.entities

import com.siro.mystrava.core.utils.getAveragePaceString
import com.siro.mystrava.core.utils.getDate
import com.siro.mystrava.core.utils.getDistanceString
import com.siro.mystrava.core.utils.getElevationString
import com.siro.mystrava.core.utils.getTimeStringHoursAndMinutes
import com.siro.mystrava.data.models.activites.ActivityItem
import com.siro.mystrava.presentation.screens.home.widgets.getStats
import com.siro.mystrava.presentation.viewmodels.*
import java.time.Month
import java.time.format.TextStyle
import java.util.Locale

data class CalendarActivities(
    val currentMonthActivities: List<ActivityItem> = emptyList(),
    val previousMonthActivities: List<ActivityItem> = emptyList(),
    val twoMonthAgoActivities: List<ActivityItem> = emptyList(),
    val currentYearActivities: List<ActivityItem> = emptyList(),
    val previousYearActivities: List<ActivityItem> = emptyList(),
    val twoYearsAgoActivities: List<ActivityItem> = emptyList(),
    val preferredActivityType: ActivityType,
    val selectedUnitType: UnitType,
    val preferredMeasureType: MeasureType,
    val relativePrevPrevMonthActivities: List<ActivityItem> = emptyList(),
    val relativePreviousMonthActivities: List<ActivityItem> = emptyList(),
    val relativeYearActivities: List<ActivityItem> = emptyList(),
    val relativePreviousYearActivities: List<ActivityItem> = emptyList(),
    val relativeTwoYearsAgoActivities: List<ActivityItem> = emptyList(),
    val relativeMonthActivities: List<ActivityItem> = emptyList()
) {
    val lastTwoMonthsActivities: List<ActivityItem> =
        currentMonthActivities.plus(previousMonthActivities)

    val calendarData = CalendarData()

    val weeklyDistanceMap: Pair<SummaryInfo, MutableMap<Int, Int>> = loadWeeklyDistanceMap()

    val monthlySummaryMetrics = buildList {
        if (preferredMeasureType == MeasureType.Absolute) {
            add(currentMonthActivities.getStats(preferredActivityType))
            add(previousMonthActivities.getStats(preferredActivityType))
            add(twoMonthAgoActivities.getStats(preferredActivityType))
        } else {
            add(relativeMonthActivities.getStats(preferredActivityType))
            add(relativePreviousMonthActivities.getStats(preferredActivityType))
            add(relativePrevPrevMonthActivities.getStats(preferredActivityType))
        }
    }

    lateinit var weeklyActivityIds: MutableList<Long>

    private fun loadWeeklyDistanceMap(): Pair<SummaryInfo, MutableMap<Int, Int>> {
        val activitiesForTheWeek = mutableListOf<ActivityItem>()
        calendarData.currentWeek.forEach { (monthInt, dayOfMonth) ->
            activitiesForTheWeek.addAll(
                lastTwoMonthsActivities.filter { activity ->
                    val date = activity.start_date.getDate()
                    //Filter out activities by the current month and day of month with type
                    if (preferredActivityType.name == ActivityType.All.name) {
                        date.monthValue == monthInt && date.dayOfMonth == dayOfMonth
                    } else {
                        date.monthValue == monthInt && date.dayOfMonth == dayOfMonth
                                && preferredActivityType.name == activity.type
                    }
                })
        }

        val totalWeeklyDistance = activitiesForTheWeek.sumOf { it.distance.toDouble() }.toFloat()
        val totalWeeklyTime = activitiesForTheWeek.sumOf { it.moving_time }

        val weeklySummaryInfoData = SummaryInfo(
            widgetTitle = " | ${
                Month.of(calendarData.currentWeek[0].first).getDisplayName(
                    TextStyle.SHORT_STANDALONE,
                    Locale.getDefault()
                )
            } ${calendarData.currentWeek[0].second}-" +
                    "${
                        Month.of(calendarData.currentWeek.last().first).getDisplayName(
                            TextStyle.SHORT_STANDALONE,
                            Locale.getDefault()
                        )
                    }  ${calendarData.currentWeek.last().second}",
            distance = totalWeeklyDistance.getDistanceString(selectedUnitType),
            totalTime = totalWeeklyTime.getTimeStringHoursAndMinutes(),
            elevation = activitiesForTheWeek
                .filter { it.type == preferredActivityType.name }
                .sumOf { it.total_elevation_gain.toDouble() }.toFloat()
                .getElevationString(selectedUnitType),
            avgPace = getAveragePaceString(totalWeeklyDistance, totalWeeklyTime, selectedUnitType),
            totalCount = activitiesForTheWeek
                .count().toString()
        )

        //Build map of dayOfMonth to distance
        val distanceByDay = activitiesForTheWeek.associateBy({
            it.start_date.getDate().dayOfMonth
        }, { activity ->
            activity.distance.toInt()
        })

        //List of weeklyActivityIds
        weeklyActivityIds =
            activitiesForTheWeek
                .filter { it.type == preferredActivityType.name }
                .map { it.id }
                .toMutableList()

        return weeklySummaryInfoData to distanceByDay.toMutableMap()
    }
}

data class SummaryInfo(
    val widgetTitle: String,
    val distance: String,
    val elevation: String,
    val totalTime: String,
    val avgPace: String,
    val totalCount: String,
)