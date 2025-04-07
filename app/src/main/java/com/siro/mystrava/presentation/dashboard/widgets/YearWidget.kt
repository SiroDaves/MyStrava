package com.siro.mystrava.presentation.dashboard.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.siro.mystrava.presentation.viewmodels.ActivityType
import com.siro.mystrava.presentation.dashboard.MyStravaWidgetCard
import com.siro.mystrava.presentation.dashboard.SummaryMetrics
import com.siro.mystrava.presentation.viewmodels.UnitType
import com.siro.mystrava.core.utils.getAveragePaceString
import com.siro.mystrava.core.utils.getDistanceString
import com.siro.mystrava.core.utils.getElevationString
import com.siro.mystrava.core.utils.getTimeStringHoursAndMinutes

@Composable
fun YearWidget(
    yearMetrics: SummaryMetrics,
    selectedActivityType: ActivityType?,
    selectedUnitType: UnitType?,
    isLoading: Boolean,
) {
    MyStravaWidgetCard(
        content = {
            Column(
                modifier = Modifier
                    .padding(
                        vertical = 12.dp,
                        horizontal = 10.dp
                    )
                    .fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("Total Miles", style = MaterialTheme.typography.bodyMedium)
                        Text(
                            yearMetrics.totalDistance.getDistanceString(selectedUnitType!!),
                            style = MaterialTheme.typography.headlineLarge
                        )

                        Text("Avg Pace", style = MaterialTheme.typography.bodyMedium)
                        Text(
                            getAveragePaceString(
                                yearMetrics.totalDistance,
                                yearMetrics.totalTime,
                                selectedUnitType
                            ),
                            style = MaterialTheme.typography.headlineLarge
                        )
                    }

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("Total Elevation", style = MaterialTheme.typography.bodyMedium)
                        Text(
                            yearMetrics.totalElevation.getElevationString(selectedUnitType!!),
                            style = MaterialTheme.typography.headlineLarge
                        )

                        Text("Avg Distance/Run", style = MaterialTheme.typography.bodyMedium)
                        Text(
                            yearMetrics.totalDistance.div(yearMetrics.count)
                                .getDistanceString(selectedUnitType!!),
                            style = MaterialTheme.typography.headlineLarge
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center){
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("Time spent", style = MaterialTheme.typography.bodyMedium)

                        Text(
                            yearMetrics.totalTime.getTimeStringHoursAndMinutes(),
                            style = MaterialTheme.typography.headlineLarge
                        )
                    }
                }

//                BoxWithConstraints(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(horizontal = 16.dp)
//                        .height(32.dp)
//                ) {
//                    Icon(
//                        painter = painterResource(id = R.drawable.ic_earth),
//                        contentDescription = "Earth Icon",
//                        modifier = Modifier.align(Alignment.BottomStart),
//                        tint = MaterialTheme.colorScheme.onSurface
//                    )
//
//                    val pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
//                    val color = MaterialTheme.colorScheme.onSurface
//
//                    val width = this.maxWidth - 60.dp
//
//                    Canvas(
//                        Modifier
//                            .width(width)
//                            .height(24.dp)
//                            .align(alignment = Alignment.Center)
//                    ) {
//                        drawArc(
//                            color = color,
//                            startAngle = 0f,
//                            sweepAngle = -180f,
//                            useCenter = false,
//                            style = Stroke(width = 4f, pathEffect = pathEffect),
//                        )
//                    }
//
//                    val percentage = yearMetrics.totalDistance.div(420000)
//                    Log.d("TAG", "YearWidget: $percentage")
//
//                    val offset = width.times(percentage)
//
//                    Box(
//                        modifier = Modifier
//                            .align(Alignment.Center)
//                            .width(width)
//                            .height(24.dp)
//                    ) {
//                        Icon(
//                            painter = painterResource(id = R.drawable.icn_running),
//                            contentDescription = "",
//                            tint = MaterialTheme.colorScheme.onSurface,
//                            modifier = Modifier.offset(x = offset, y = -12.dp).size(20.dp).align(Alignment.TopStart)
//                        )
//                    }
//
//                    Icon(
//                        painter = painterResource(id = R.drawable.ic_satelite),
//                        contentDescription = "Earth Icon",
//                        modifier = Modifier.align(Alignment.BottomEnd),
//                        tint = MaterialTheme.colorScheme.onSurface
//                    )
//                }
//
//                Spacer(modifier = Modifier.height(8.dp))
//
//                Row(
//                    modifier = Modifier.fillMaxWidth(),
//                    horizontalArrangement = Arrangement.Center
//                ) {
//                    Text("Remaining to Space Station: ")
//
//                    Text(
//                        420000.minus(yearMetrics.totalDistance)
//                            .getDistanceString(selectedUnitType!!, isYearSummary = true)
//                    )
//                }

            }
        })
}