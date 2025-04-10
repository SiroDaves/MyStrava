package com.siro.mystrava.presentation.widgets.compares

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.siro.mystrava.R
import com.siro.mystrava.core.utils.*
import com.siro.mystrava.domain.entities.*
import com.siro.mystrava.presentation.screens.home.*
import com.siro.mystrava.presentation.screens.home.widgets.*
import com.siro.mystrava.presentation.viewmodels.*

@Composable
fun CompareWidget(
    dashboardType: DashboardType,
    selectedActivityType: ActivityType?,
    columnTitles: Array<String>,
    prevMetrics: SummaryMetrics,
    prevPrevMetrics: SummaryMetrics,
    currentMonthMetrics: SummaryMetrics,
    selectedUnitType: UnitType?
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
                            fontWeight = FontWeight.ExtraBold
                        )

                        MonthTextStat(
                            columnTitles[0],
                            monthColumnWidth = monthColumnWidth
                        )

                        Spacer(modifier = Modifier.width(monthColumnWidth))

                        MonthTextStat(
                            columnTitles[1],
                            monthColumnWidth = monthColumnWidth
                        )
                        Spacer(modifier = Modifier.width(monthColumnWidth))

                        MonthTextStat(
                            columnTitles[2],
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
                            modifier = Modifier.width(firstColumnWidth)
                        )

                        MonthTextStat(
                            currentMonthMetrics.totalDistance.getDistanceString(
                                selectedUnitType!!,
                                isYearSummary = dashboardType == DashboardType.Year
                            ),
                            monthColumnWidth = monthColumnWidth
                        )

                        PercentDelta(
                            now = currentMonthMetrics.totalDistance.toInt(),
                            then = prevMetrics.totalDistance.toInt(),
                            monthColumnWidth = monthColumnWidth,
                            type = StatType.Distance
                        )

                        MonthTextStat(
                            prevMetrics.totalDistance.getDistanceString(
                                selectedUnitType!!,
                                isYearSummary = dashboardType == DashboardType.Year
                            ),
                            monthColumnWidth = monthColumnWidth
                        )

                        PercentDelta(
                            now = prevMetrics.totalDistance.toInt(),
                            then = prevPrevMetrics.totalDistance.toInt(),
                            monthColumnWidth = monthColumnWidth,
                            type = StatType.Distance
                        )

                        MonthTextStat(
                            prevPrevMetrics.totalDistance.getDistanceString(
                                selectedUnitType!!,
                                isYearSummary = dashboardType == DashboardType.Year
                            ),
                            monthColumnWidth = monthColumnWidth
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
                            currentMonthMetrics.totalTime.getTimeStringHoursAndMinutes(),
                            monthColumnWidth = monthColumnWidth
                        )

                        PercentDelta(
                            now = currentMonthMetrics.totalTime,
                            then = prevMetrics.totalTime,
                            monthColumnWidth = monthColumnWidth,
                            type = StatType.Time
                        )

                        MonthTextStat(
                            prevMetrics.totalTime.getTimeStringHoursAndMinutes(),
                            monthColumnWidth = monthColumnWidth
                        )

                        PercentDelta(
                            now = prevMetrics.totalTime,
                            then = prevPrevMetrics.totalTime,
                            monthColumnWidth = monthColumnWidth,
                            type = StatType.Time
                        )
                        MonthTextStat(
                            prevPrevMetrics.totalTime.getTimeStringHoursAndMinutes(),
                            monthColumnWidth = monthColumnWidth
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
                            currentMonthMetrics.totalElevation.getElevationString(selectedUnitType!!),
                            monthColumnWidth = monthColumnWidth
                        )

                        PercentDelta(
                            now = currentMonthMetrics.totalElevation.toInt(),
                            then = prevMetrics.totalElevation.toInt(),
                            monthColumnWidth = monthColumnWidth,
                            type = StatType.Count
                        )

                        MonthTextStat(
                            prevMetrics.totalElevation.getElevationString(selectedUnitType!!),
                            monthColumnWidth = monthColumnWidth
                        )

                        PercentDelta(
                            now = prevMetrics.totalElevation.toInt(),
                            then = prevPrevMetrics.totalElevation.toInt(),
                            monthColumnWidth = monthColumnWidth,
                            type = StatType.Count
                        )

                        MonthTextStat(
                            prevPrevMetrics.totalElevation.getElevationString(selectedUnitType!!),
                            monthColumnWidth = monthColumnWidth
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
                            "${currentMonthMetrics.count}",
                            monthColumnWidth = monthColumnWidth
                        )
                        PercentDelta(
                            now = currentMonthMetrics.count,
                            then = prevMetrics.count,
                            monthColumnWidth = monthColumnWidth,
                            type = StatType.Count
                        )
                        MonthTextStat(
                            "${prevMetrics.count}",
                            monthColumnWidth = monthColumnWidth
                        )

                        PercentDelta(
                            now = prevMetrics.count,
                            then = prevPrevMetrics.count,
                            monthColumnWidth = monthColumnWidth,
                            type = StatType.Count
                        )

                        MonthTextStat(
                            "${prevPrevMetrics.count}",
                            monthColumnWidth = monthColumnWidth
                        )
                    }
                }
            }
        })
}

enum class DashboardType { Week, Month, Year }