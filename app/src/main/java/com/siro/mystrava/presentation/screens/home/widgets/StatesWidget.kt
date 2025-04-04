package com.siro.mystrava.presentation.screens.home.widgets

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.siro.mystrava.presentation.viewmodels.*

@Composable
fun SuccessState(
    state: ActivityUiState.DataLoaded,
    viewModel: HomeViewModel,
    paddingValues: PaddingValues,
    selectedActivityType: ActivityType,
    selectedUnitType: UnitType,
    //updateMonthlyMetrics: (SummaryMetrics) -> Unit,
    onShowWeeklyDetails: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(paddingValues)
            .verticalScroll(rememberScrollState())
    ) {
        /*WeekSummarySection(state, viewModel, onShowWeeklyDetails)
        MonthSummarySection(state, viewModel, selectedActivityType, selectedUnitType, updateMonthlyMetrics)
        WeekComparisonSection(state, viewModel, selectedActivityType, selectedUnitType)
        MonthComparisonSection(state, viewModel, selectedActivityType, selectedUnitType)
        YearToDateSection(state, selectedActivityType, selectedUnitType)
        YearComparisonSection(viewModel, selectedActivityType, selectedUnitType)
        PoweredByStravaFooter()*/
    }
}

@Composable
fun LoadingState(refreshState: SwipeRefreshState) {
    if (refreshState.isRefreshing) {
        Box(modifier = Modifier.fillMaxSize())
    }
}

@Composable
fun ErrorState(state: ActivityUiState.Error) {
    if (state.errorMessage.isNotEmpty()) {
        Snackbar(
            action = { Text("Refresh") },
            modifier = Modifier.padding(16.dp)
        ) {
            Text(state.errorMessage)
        }
    }
}