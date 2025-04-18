package com.siro.mystrava.presentation.screens.settings

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.siro.mystrava.presentation.viewmodels.*

@OptIn(ExperimentalMaterial3Api::class)
@ExperimentalComposeUiApi
@Composable
fun SettingsScreen(
    viewModel: HomeViewModel,
    selectedActivityType: ActivityType?,
    selectedUnitType: UnitType?,
    selectedMeasureType: MeasureType?
) {
    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .height(56.dp)
                    .fillMaxWidth()
                    .background(color = MaterialTheme.colorScheme.surface),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    "Settings",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center,
                )
            }
        },
        content = {
            Box() {

                Column(
                    modifier = Modifier
                        .padding(it)
                        .background(color = MaterialTheme.colorScheme.surface)
                        .fillMaxHeight()
                        .verticalScroll(rememberScrollState())
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp, horizontal = 24.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(
                                text = "Activities",
                                style = MaterialTheme.typography.headlineMedium,
                                color = MaterialTheme.colorScheme.onSurface
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            Column(
                                modifier = Modifier
                                    .padding(horizontal = 16.dp)
                                    .background(
                                        color = MaterialTheme.colorScheme.onPrimary,
                                        shape = RoundedCornerShape(20.dp)
                                    )
                            ) {
                                ActivityType.values().forEach { activityType ->
                                    Row(
                                        modifier = Modifier
                                            .clickable {
                                                //viewModel.updateSelectedActivity(activityType = activityType)
                                            }
                                            .fillMaxWidth()
                                            .padding(horizontal = 16.dp)
                                            .height(40.dp),

                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            activityType.name,
                                            color = MaterialTheme.colorScheme.onSurface
                                        )

                                        selectedActivityType?.let {
                                            if (it.name == activityType.name) {
                                                Icon(
                                                    imageVector = Icons.Default.Check,
                                                    contentDescription = "Currently Selected",
                                                    tint = MaterialTheme.colorScheme.onSurface
                                                )
                                            }
                                        }
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            Text(
                                text = "Units",
                                style = MaterialTheme.typography.headlineMedium,
                                color = MaterialTheme.colorScheme.onSurface
                            )

                            Spacer(modifier = Modifier.height(16.dp))
                            Column(
                                modifier = Modifier
                                    .padding(horizontal = 16.dp)
                                    .background(
                                        color = MaterialTheme.colorScheme.onPrimary,
                                        shape = RoundedCornerShape(20.dp)
                                    )
                            ) {
                                Row(
                                    modifier = Modifier
                                        .clickable {
                                            //viewModel.updateSelectedUnit(UnitType.Imperial)
                                        }
                                        .fillMaxWidth()
                                        .padding(horizontal = 16.dp)
                                        .height(40.dp),

                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text("Imperial", color = MaterialTheme.colorScheme.onSurface)

                                    if (UnitType.Imperial.name == selectedUnitType?.name) {
                                        Icon(
                                            imageVector = Icons.Default.Check,
                                            contentDescription = "Currently Selected",
                                            tint = MaterialTheme.colorScheme.onSurface
                                        )
                                    }
                                }

                                Row(
                                    modifier = Modifier
                                        .clickable {
                                            //viewModel.updateSelectedUnit(UnitType.Metric)
                                        }
                                        .fillMaxWidth()
                                        .padding(horizontal = 16.dp)
                                        .height(40.dp),

                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text("Metric", color = MaterialTheme.colorScheme.onSurface)

                                    if (UnitType.Metric.name == selectedUnitType?.name) {
                                        Icon(
                                            imageVector = Icons.Default.Check,
                                            contentDescription = "Currently Selected",
                                            tint = MaterialTheme.colorScheme.onSurface
                                        )
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.height(16.dp))

                            Text(
                                text = "Measure Type",
                                style = MaterialTheme.typography.headlineMedium,
                                color = MaterialTheme.colorScheme.onSurface
                            )

                            Spacer(modifier = Modifier.height(16.dp))
                            Column(
                                modifier = Modifier
                                    .padding(horizontal = 16.dp)
                                    .background(
                                        color = MaterialTheme.colorScheme.onPrimary,
                                        shape = RoundedCornerShape(20.dp)
                                    )
                            ) {
                                Row(
                                    modifier = Modifier
                                        .clickable {
                                            //viewModel.updateMeasureType(MeasureType.Absolute)
                                        }
                                        .fillMaxWidth()
                                        .padding(horizontal = 16.dp)
                                        .height(40.dp),

                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text("Absolute", color = MaterialTheme.colorScheme.onSurface)

                                    if (MeasureType.Absolute.name == selectedMeasureType?.name) {
                                        Icon(
                                            imageVector = Icons.Default.Check,
                                            contentDescription = "Currently Selected",
                                            tint = MaterialTheme.colorScheme.onSurface
                                        )
                                    }
                                }

                                Row(
                                    modifier = Modifier
                                        .clickable {
                                            //viewModel.updateMeasureType(MeasureType.Relative)
                                        }
                                        .fillMaxWidth()
                                        .padding(horizontal = 16.dp)
                                        .height(40.dp),

                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text("Relative", color = MaterialTheme.colorScheme.onSurface)

                                    if (MeasureType.Relative.name == selectedMeasureType?.name) {
                                        Icon(
                                            imageVector = Icons.Default.Check,
                                            contentDescription = "Currently Selected",
                                            tint = MaterialTheme.colorScheme.onSurface
                                        )
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.height(16.dp))

                        }
                    }

                    /*val widgetStatus by viewModel.widgetStatus
                    if (widgetStatus) {
                        Log.d("TAG", "MyStravaSettingsView: TRUE")
                    }*/

                    Box(
                        modifier = Modifier
                            .padding(24.dp),
                        contentAlignment = Alignment.BottomCenter
                    ) {
                        Button(
                            onClick = { viewModel.logout() },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(text = "Log out")
                        }
                    }
                }

            }
        }
    )
}