package com.siro.mystrava.presentation.screens.home

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import com.google.accompanist.swiperefresh.*
import com.siro.mystrava.presentation.screens.home.widgets.*
import com.siro.mystrava.presentation.theme.primaryColor
import com.siro.mystrava.presentation.viewmodels.*

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier
) {
    var fetchData by rememberSaveable { mutableStateOf(0) }
    if (fetchData == 0) {
        viewModel.fetchData()
        fetchData = fetchData.inc()
    }

    val activityUiState by viewModel.activityUiState.collectAsState()
    val selectedActivityType by viewModel.activityType.observeAsState(ActivityType.Run)
    val selectedUnitType by viewModel.unitType.observeAsState(UnitType.Imperial)
    val preferredMeasureType by viewModel.measureType.observeAsState(initial = MeasureType.Absolute)
    val refreshState = rememberSwipeRefreshState(false)
    var showWeeklyDetailSnapshot by remember { mutableStateOf(false) }
    val weeklySnapshotDetails by viewModel.weeklyActivityDetails.observeAsState(emptyList())

    /*var currentMonthMetrics by remember { mutableStateOf(SummaryMetrics()) }
    val updateMonthlyMetrics = { summaryMetrics: SummaryMetrics ->
        currentMonthMetrics = summaryMetrics
    }*/

    Scaffold(
        topBar = {
            Text(
                "My Strava",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        },
        modifier = modifier
    ) { innerPadding ->
        HomeContent(
            viewModel = viewModel,
            activityUiState = activityUiState,
            refreshState = refreshState,
            innerPadding = innerPadding,
            paddingValues = paddingValues,
            selectedActivityType = selectedActivityType,
            selectedUnitType = selectedUnitType,
            //showWeeklyDetailSnapshot = showWeeklyDetailSnapshot,
            //weeklySnapshotDetails = weeklySnapshotDetails,
            //updateMonthlyMetrics = updateMonthlyMetrics,
            //onShowWeeklyDetails = { showWeeklyDetailSnapshot = true },
            //onDismissWeeklyDetails = { showWeeklyDetailSnapshot = false }
        )
    }
}

@Composable
private fun HomeContent(
    viewModel: HomeViewModel,
    activityUiState: ActivityUiState,
    refreshState: SwipeRefreshState,
    innerPadding: PaddingValues,
    paddingValues: PaddingValues,
    selectedActivityType: ActivityType,
    selectedUnitType: UnitType,
    //showWeeklyDetailSnapshot: Boolean,
    //weeklySnapshotDetails: List<ActivitiesItem>,
    //updateMonthlyMetrics: (SummaryMetrics) -> Unit,
    //onShowWeeklyDetails: () -> Unit,
    //onDismissWeeklyDetails: () -> Unit,
) {
    Box(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surface)
    ) {
        SwipeRefresh(
            state = refreshState,
            onRefresh = viewModel::fetchData,
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
                is ActivityUiState.Error -> ErrorState(state)
                is ActivityUiState.Loading -> LoadingState(refreshState)
                is ActivityUiState.DataLoaded -> SuccessState(
                    state = state,
                    viewModel = viewModel,
                    paddingValues = paddingValues,
                    selectedActivityType = selectedActivityType,
                    selectedUnitType = selectedUnitType,
                    //updateMonthlyMetrics = updateMonthlyMetrics,
                    //onShowWeeklyDetails = onShowWeeklyDetails
                )
            }
        }

        /*if (showWeeklyDetailSnapshot) {
            WeeklyDetailDialog(
                weeklySnapshotDetails = weeklySnapshotDetails,
                onDismissRequest = onDismissWeeklyDetails
            )
        }*/
    }
}
