package com.siro.mystrava.presentation.home

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.google.accompanist.swiperefresh.*
import com.siro.mystrava.presentation.home.widgets.*
import com.siro.mystrava.presentation.theme.primaryColor
import com.siro.mystrava.presentation.viewmodels.*

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@ExperimentalComposeUiApi
@ExperimentalFoundationApi
@Composable
fun HomeScreen(viewModel: HomeViewModel) {
    var fetchData by rememberSaveable { mutableStateOf(0) }

    if (fetchData == 0) {
        viewModel.fetchData()
        fetchData = fetchData.inc()
    }

    val activities by viewModel.activities.collectAsState(initial = emptyList())
    val activityUiState by viewModel.activityUiState.collectAsState()
    val selectedActivityType by viewModel.activityType.observeAsState(ActivityType.Run)
    val selectedUnitType by viewModel.unitType.observeAsState(UnitType.Imperial)

    var refreshState = rememberSwipeRefreshState(false)

    var showWeeklyDetailSnapshot by remember { mutableStateOf(false) }
    val weeklySnapshotDetails by viewModel.weeklyActivityDetails.observeAsState(emptyList())

    val isRefreshing = activityUiState is ActivityUiState.Loading
    val pullRefreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = { viewModel.fetchData() }
    )
    Scaffold(
        content = {
            Box(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
                    .background(color = MaterialTheme.colorScheme.surface)
            ) {
                when (val state = activityUiState) {
                    is ActivityUiState.Error -> {
                        refreshState.isRefreshing = false
                        ErrorState(
                            errorMessage = state.errorMessage,
                            onRetry = { viewModel.fetchData() }
                        )
                    }

                    is ActivityUiState.Loading -> {
                        refreshState.isRefreshing = true
                        LoadingState()
                    }

                    is ActivityUiState.DataLoaded -> {
                        refreshState.isRefreshing = false
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(10.dp),
                            contentPadding = PaddingValues(horizontal = 10.dp)
                        ) {
                            item {
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
                            }

                            if (showWeeklyDetailSnapshot) {
                                item {
                                    Dialog(onDismissRequest = {
                                        showWeeklyDetailSnapshot = false
                                    }) {
                                        HomeScreenWidgets2(
                                            weeklySnapshotDetails = weeklySnapshotDetails
                                        )
                                    }
                                }
                            }

                            items(activities) { activity ->
                                Workout(
                                    activity = activity,
                                    onActivityClick = { clickedActivity ->
                                        // Handle the click, e.g., navigate to activity details
                                        // viewModel.navigateToActivityDetails(clickedActivity.activityId)
                                    },
                                )
                            }
                        }
                    }
                }
                PullRefreshIndicator(
                    refreshing = isRefreshing,
                    state = pullRefreshState,
                    modifier = Modifier.align(Alignment.TopCenter),
                    contentColor = primaryColor,
                )
            }
        }
    )
}
