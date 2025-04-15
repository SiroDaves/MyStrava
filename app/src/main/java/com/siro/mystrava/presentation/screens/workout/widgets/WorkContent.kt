package com.siro.mystrava.presentation.screens.workout.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.siro.mystrava.core.utils.*
import com.siro.mystrava.data.models.detail.ActivityDetail

@Composable
fun WorkoutContent(activity: ActivityDetail) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = PaddingValues(horizontal = 10.dp)
    ) {
        val distance = activity.distance / 1000
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = activity.name,
                        style = MaterialTheme.typography.headlineSmall
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = formatActivityDate(activity.start_date_local),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        Text(
                            text = activity.type,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    MetricItem(
                        value = "${"%.1f".format(distance)} km",
                        label = "Distance"
                    )

                    MetricItem(
                        value = formatElapsedTime(activity.moving_time),
                        label = "Duration"
                    )

                    MetricItem(
                        value = "${activity.calories ?: 0}",
                        label = "Calories"
                    )
                }
            }
        }

        // Pace and heart rate metrics
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Performance",
                        style = MaterialTheme.typography.titleMedium
                    )

                    activity.average_speed.let {
                        DetailRow(
                            label = "Average Pace",
                            value = calculatePace(activity.moving_time, distance)
                        )
                    }

                    activity.average_heartrate.let {
                        DetailRow(
                            label = "Average Heart Rate",
                            value = "${it.toInt()} bpm"
                        )
                    }

                    /*activity.max?.let {
                        DetailRow(
                            label = "Max Heart Rate",
                            value = "${it.toInt()} bpm"
                        )
                    }*/

                    activity.total_elevation_gain.let {
                        DetailRow(
                            label = "Elevation Gain",
                            value = "${it.toInt()} m"
                        )
                    }
                }
            }
        }

        // Map (if activity has map data)
        /*activity.map?.let { map ->
            if (!map.summaryPolyline.isNullOrEmpty()) {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = "Route Map",
                                style = MaterialTheme.typography.titleMedium
                            )

                            // Placeholder for the map view
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp)
                                    .background(Color.LightGray.copy(alpha = 0.3f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = "Map View")
                            }
                        }
                    }
                }
            }
        }*/

        // Charts section (placeholder)
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Performance Charts",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Heart Rate Chart placeholder
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp)
                            .background(Color.LightGray.copy(alpha = 0.3f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "Heart Rate Chart")
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Pace Chart placeholder
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp)
                            .background(Color.LightGray.copy(alpha = 0.3f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "Pace Chart")
                    }
                }
            }
        }

        // Description section (if exists)
        activity.description.let {
            if (it.isNotEmpty()) {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = "Description",
                                style = MaterialTheme.typography.titleMedium
                            )

                            Text(
                                text = it,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }
        }

        item { CopyableLinkCard(activity.id.toString()) }
    }
}
