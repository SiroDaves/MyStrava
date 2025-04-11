package com.siro.mystrava.presentation.widgets.summaries

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.unit.dp
import com.siro.mystrava.presentation.viewmodels.*
import com.siro.mystrava.core.utils.*
import com.siro.mystrava.presentation.screens.home.MyStravaWidgetCard
import com.siro.mystrava.presentation.screens.home.widgets.SummaryMetrics

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

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
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
            }
        })
}