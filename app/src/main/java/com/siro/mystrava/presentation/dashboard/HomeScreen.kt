package com.siro.mystrava.presentation.dashboard

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.window.Dialog
import com.google.accompanist.swiperefresh.*
import com.siro.mystrava.presentation.theme.primaryColor
import com.siro.mystrava.presentation.viewmodels.*

@OptIn(ExperimentalMaterial3Api::class)
@ExperimentalComposeUiApi
@ExperimentalFoundationApi
@Composable
fun HomeScreen(viewModel: HomeViewModel) {
    var fetchData by rememberSaveable { mutableStateOf(0) }

    if (fetchData == 0) {
        viewModel.fetchData()
        fetchData = fetchData.inc()
    }

    val activityUiState by viewModel.activityUiState.collectAsState()
    val selectedActivityType by viewModel.activityType.observeAsState(ActivityType.Run)
    val selectedUnitType by viewModel.unitType.observeAsState(UnitType.Imperial)

    var refreshState = rememberSwipeRefreshState(false)

    var showWeeklyDetailSnapshot by remember { mutableStateOf(false) }
    val weeklySnapshotDetails by viewModel.weeklyActivityDetails.observeAsState(emptyList())

    Scaffold(
        content = {
            Box(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
                    .background(color = MaterialTheme.colorScheme.surface)
            ) {
                SwipeRefresh(
                    state = refreshState,
                    onRefresh = { viewModel.fetchData() },
                    indicator = { s, trigger ->
                        SwipeRefreshIndicator(
                            s,
                            trigger,
                            contentColor = primaryColor,
                            backgroundColor = Color.White
                        )
                    },
                ) {
                    when (val state = activityUiState) {
                        is ActivityUiState.Error -> {
                            if (state.errorMessage.isNotEmpty()) {
                                Snackbar(action = { Text(text = "Refresh") }) {
                                    Text(text = state.errorMessage)
                                }
                            }
                        }

                        is ActivityUiState.Loading -> {
                            refreshState.isRefreshing = true
                            Column(modifier = Modifier.fillMaxSize()) { }
                        }

                        is ActivityUiState.DataLoaded -> {
                            refreshState.isRefreshing = false
                            Box() {
                                HomeScreenWidgets1(
                                    state = state,
                                    viewModel = viewModel,
                                    selectedActivityType = selectedActivityType,
                                    selectedUnitType = selectedUnitType,
                                    onWeeklySnapshotClick = {
                                        viewModel.loadWeekActivityDetails(state.calendarActivities.weeklyActivityIds.map { activityId -> activityId.toString() })
                                        showWeeklyDetailSnapshot = true
                                    }
                                )

                                if (showWeeklyDetailSnapshot) {
                                    Dialog(onDismissRequest = {
                                        showWeeklyDetailSnapshot = false
                                    }) {
                                        HomeScreenWidgets2(
                                            weeklySnapshotDetails = weeklySnapshotDetails
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    )
}
