package com.siro.mystrava.presentation.screens.workout

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import com.siro.mystrava.core.utils.calculatePace
import com.siro.mystrava.core.utils.formatActivityDate
import com.siro.mystrava.core.utils.formatElapsedTime
import com.siro.mystrava.domain.entities.UiState
import com.siro.mystrava.presentation.widgets.ErrorState
import com.siro.mystrava.presentation.widgets.LoadingState
import com.siro.mystrava.presentation.viewmodels.WorkoutViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutScreen(
    viewModel: WorkoutViewModel,
    activityId: String,
    onBackPressed: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val activityDetail by viewModel.activity.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Workout Details") },
                /*navigationIcon = {
                    IconButton(onClick = onBackPressed) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }*/
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            when (uiState) {
                is UiState.Loading -> {
                    LoadingState()
                }

                is UiState.Error -> {
                    val errorMessage = (uiState as UiState.Error).errorMessage
                    ErrorState(
                        errorMessage = errorMessage,
                        onRetry = { viewModel.fetchActivityDetails(activityId) }
                    )
                }

                else -> {
                    activityDetail?.let { activity ->
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
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

                            // Key metrics
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
                        }
                    } ?: run {
                        // Show error if activity is null but state is not Loading or Error
                        ErrorState(
                            errorMessage = "Activity details not found",
                            onRetry = { viewModel.fetchActivityDetails(activityId) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MetricItem(value: String, label: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = value,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun DetailRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold
        )
    }
}
