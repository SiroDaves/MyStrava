package com.siro.mystrava.presentation.widgets.compares

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.siro.mystrava.R
import com.siro.mystrava.core.utils.*
import com.siro.mystrava.data.models.activites.ActivityItem
import com.siro.mystrava.domain.entities.*
import com.siro.mystrava.presentation.screens.home.*
import com.siro.mystrava.presentation.screens.home.widgets.*
import com.siro.mystrava.presentation.viewmodels.*

@Composable
fun WeekCompareWidget(
    activitesList: List<ActivityItem>,
    selectedActivityType: ActivityType?,
    selectedUnitType: UnitType?,
    today: Int?,
    monthWeekMap: MutableMap<Int, MutableList<Pair<Int, Int>>>,
    isLoading: Boolean,
) {
    MyStravaWidgetCard(
        content = {
            BoxWithConstraints(
                modifier = Modifier.padding(
                    vertical = 12.dp,
                    horizontal = 10.dp
                )
            ) {
                val firstColumnWidth = maxWidth.times(.12f)
                val monthColumnWidth = (maxWidth - firstColumnWidth) / 5

                if (monthWeekMap.isNotEmpty()) {

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.Center
                    ) {
                        //Header Row
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = selectedActivityType?.name ?: "",
                                color = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.width(firstColumnWidth),
                                style = MaterialTheme.typography.bodySmall,
                                textAlign = TextAlign.Start,
                                fontWeight = FontWeight.ExtraBold,
                            )

                            MonthTextStat(
                                "Current",
                                monthColumnWidth = monthColumnWidth
                            )

                            Spacer(modifier = Modifier.width(monthColumnWidth))

                            MonthTextStat(
                                "1w ago",
                                monthColumnWidth = monthColumnWidth
                            )
                            Spacer(modifier = Modifier.width(monthColumnWidth))

                            MonthTextStat(
                                "2w ago",
                                monthColumnWidth = monthColumnWidth
                            )
                        }

                        Divider(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 2.dp),
                            thickness = 1.dp,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        val weeklyDataMap: MutableList<SummaryMetrics> =
                            mutableListOf()

                        val weeklyActivitiesMap: MutableList<Pair<Int, MutableList<ActivityItem>>> =
                            mutableListOf()

                        var startingWeekInMap: MutableList<Int> = mutableListOf()
                        monthWeekMap.forEach loop@{ weekCount, datesInWeek ->
                            datesInWeek.forEach { week ->
                                if (week.second == today!!) {
                                    startingWeekInMap.add(weekCount)
                                }
                            }
                        }

                        Log.d("TAG", "WeekCompareWidget: starting week map $startingWeekInMap")

                        for (i in startingWeekInMap[0] downTo (startingWeekInMap[0] - 2)) {
                            val weeklyActivitiesList =
                                mutableListOf<ActivityItem>()
                            activitesList.forEach { ActivityItem ->
                                val datesInWeek = monthWeekMap.get(i)
                                datesInWeek!!.forEach {
                                    if (it.second == ActivityItem.start_date_local.getDate().dayOfMonth &&
                                        it.first == ActivityItem.start_date_local.getDate().monthValue
                                    ) {
                                        weeklyActivitiesList.add(ActivityItem)
                                    }
                                }

                            }
                            Log.d("TAG", "WeekCompareWidget: Weekly List: $weeklyActivitiesList")
                            weeklyActivitiesMap.add(Pair(i, weeklyActivitiesList))
                        }

                        Log.d("TAG", "WeekCompareWidget: $weeklyActivitiesMap")


                        weeklyActivitiesMap.forEach { weeklyActivityMap ->
                            var count = 0
                            var distance = 0f
                            var elevation = 0f
                            var time = 0
                            weeklyActivityMap.second.forEach { ActivityItem ->
                                if (selectedActivityType!!.name == ActivityType.All.name) {
                                    count = count.inc()
                                    distance += ActivityItem.distance
                                    elevation += ActivityItem.total_elevation_gain
                                    time += ActivityItem.moving_time
                                } else if (ActivityItem.type == selectedActivityType!!.name) {
                                    count = count.inc()
                                    distance += ActivityItem.distance
                                    elevation += ActivityItem.total_elevation_gain
                                    time += ActivityItem.moving_time
                                }
                            }

                            weeklyDataMap.add(
                                SummaryMetrics(
                                    count = count,
                                    totalDistance = distance,
                                    totalElevation = elevation,
                                    totalTime = time
                                )
                            )
                            Log.d(
                                "TAG",
                                "HomeScreen: $count, ${
                                    distance.getDistanceString(
                                        selectedUnitType!!
                                    )
                                }, ${
                                    elevation.getElevationString(
                                        selectedUnitType!!
                                    )
                                }, ${time.getTimeStringHoursAndMinutes()}"
                            )
                        }

                        // Distance Row
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            DashboardStat(
                                image = R.drawable.ic_ruler,
                                modifier = Modifier.width(firstColumnWidth),
                            )

                            MonthTextStat(
                                weeklyDataMap[0].totalDistance.getDistanceString(selectedUnitType!!),
                                monthColumnWidth = monthColumnWidth,
                                isLoading = isLoading
                            )

                            PercentDelta(
                                now = weeklyDataMap[0].totalDistance.toInt(),
                                then = weeklyDataMap[1].totalDistance.toInt(),
                                monthColumnWidth = monthColumnWidth,
                                type = StatType.Distance,
                            )

                            MonthTextStat(
                                weeklyDataMap[1].totalDistance.getDistanceString(selectedUnitType!!),
                                monthColumnWidth = monthColumnWidth,
                                isLoading = isLoading
                            )

                            PercentDelta(
                                now = weeklyDataMap[1].totalDistance.toInt(),
                                then = weeklyDataMap[2].totalDistance.toInt(),
                                monthColumnWidth = monthColumnWidth,
                                type = StatType.Distance
                            )

                            MonthTextStat(
                                weeklyDataMap[2].totalDistance.getDistanceString(selectedUnitType!!),
                                monthColumnWidth = monthColumnWidth,
                                isLoading = isLoading
                            )
                        }
                        //Time Row
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            DashboardStat(
                                image = R.drawable.ic_clock_time,
                                modifier = Modifier.width(firstColumnWidth)
                            )

                            MonthTextStat(
                                weeklyDataMap[0].totalTime.getTimeStringHoursAndMinutes(),
                                monthColumnWidth = monthColumnWidth,
                                isLoading = isLoading
                            )

                            PercentDelta(
                                now = weeklyDataMap[0].totalTime,
                                then = weeklyDataMap[1].totalTime,
                                monthColumnWidth = monthColumnWidth,
                                type = StatType.Time
                            )

                            MonthTextStat(
                                weeklyDataMap[1].totalTime.getTimeStringHoursAndMinutes(),
                                monthColumnWidth = monthColumnWidth,
                                isLoading = isLoading
                            )

                            PercentDelta(
                                now = weeklyDataMap[1].totalTime,
                                then = weeklyDataMap[2].totalTime,
                                monthColumnWidth = monthColumnWidth,
                                type = StatType.Time
                            )
                            MonthTextStat(
                                weeklyDataMap[2].totalTime.getTimeStringHoursAndMinutes(),
                                monthColumnWidth = monthColumnWidth,
                                isLoading = isLoading
                            )
                        }
                        // Elevation Row
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            DashboardStat(
                                image = R.drawable.ic_up_right,
                                modifier = Modifier.width(firstColumnWidth)
                            )

                            MonthTextStat(
                                weeklyDataMap[0].totalElevation.getElevationString(selectedUnitType!!),
                                monthColumnWidth = monthColumnWidth,
                                isLoading = isLoading
                            )

                            PercentDelta(
                                now = weeklyDataMap[0].totalElevation.toInt(),
                                then = weeklyDataMap[1].totalElevation.toInt(),
                                monthColumnWidth = monthColumnWidth,
                                type = StatType.Count
                            )

                            MonthTextStat(
                                weeklyDataMap[1].totalElevation.getElevationString(selectedUnitType!!),
                                monthColumnWidth = monthColumnWidth,
                                isLoading = isLoading
                            )

                            PercentDelta(
                                now = weeklyDataMap[1].totalElevation.toInt(),
                                then = weeklyDataMap[2].totalElevation.toInt(),
                                monthColumnWidth = monthColumnWidth,
                                type = StatType.Count
                            )

                            MonthTextStat(
                                weeklyDataMap[2].totalElevation.getElevationString(selectedUnitType!!),
                                monthColumnWidth = monthColumnWidth,
                                isLoading = isLoading
                            )
                        }

                        // Elevation Row
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            DashboardStat(
                                image = R.drawable.ic_speed,
                                modifier = Modifier.width(firstColumnWidth)
                            )

                            MonthTextStat(
                                getAveragePaceString(
                                    weeklyDataMap[0].totalDistance,
                                    weeklyDataMap[0].totalTime,
                                    selectedUnitType!!
                                ),
                                monthColumnWidth = monthColumnWidth,
                                isLoading = isLoading
                            )

                            PercentDelta(
                                now = weeklyDataMap[0].totalDistance.getAveragePaceFromDistance(
                                    weeklyDataMap[0].totalTime
                                ),
                                then = weeklyDataMap[1].totalDistance.getAveragePaceFromDistance(
                                    weeklyDataMap[1].totalTime
                                ),
                                monthColumnWidth = monthColumnWidth,
                                type = StatType.Pace
                            )

                            MonthTextStat(
                                getAveragePaceString(
                                    weeklyDataMap[1].totalDistance,
                                    weeklyDataMap[1].totalTime,
                                    selectedUnitType!!
                                ),
                                monthColumnWidth = monthColumnWidth,
                                isLoading = isLoading
                            )

                            PercentDelta(
                                now = weeklyDataMap[1].totalDistance.getAveragePaceFromDistance(
                                    weeklyDataMap[1].totalTime
                                ),
                                then = weeklyDataMap[2].totalDistance.getAveragePaceFromDistance(
                                    weeklyDataMap[2].totalTime
                                ),
                                monthColumnWidth = monthColumnWidth,
                                type = StatType.Pace
                            )

                            MonthTextStat(
                                getAveragePaceString(
                                    weeklyDataMap[2].totalDistance,
                                    weeklyDataMap[2].totalTime,
                                    selectedUnitType!!
                                ),
                                monthColumnWidth = monthColumnWidth,
                                isLoading = isLoading
                            )
                        }
                        //Count Row
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            DashboardStat(
                                image = R.drawable.ic_hashtag,
                                modifier = Modifier.width(firstColumnWidth)
                            )

                            MonthTextStat(
                                "${weeklyDataMap[0].count}",
                                monthColumnWidth = monthColumnWidth,
                                isLoading = isLoading
                            )
                            PercentDelta(
                                now = weeklyDataMap[0].count,
                                then = weeklyDataMap[1].count,
                                monthColumnWidth = monthColumnWidth,
                                type = StatType.Count
                            )
                            MonthTextStat(
                                "${weeklyDataMap[1].count}",
                                monthColumnWidth = monthColumnWidth,
                                isLoading = isLoading
                            )

                            PercentDelta(
                                now = weeklyDataMap[1].count,
                                then = weeklyDataMap[2].count,
                                monthColumnWidth = monthColumnWidth,
                                type = StatType.Count
                            )

                            MonthTextStat(
                                "${weeklyDataMap[2].count}",
                                monthColumnWidth = monthColumnWidth,
                                isLoading = isLoading
                            )
                        }
                    }
                }
            }
        }
    )
}