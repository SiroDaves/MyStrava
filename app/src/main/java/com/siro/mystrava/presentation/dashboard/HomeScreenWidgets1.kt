package com.siro.mystrava.presentation.dashboard

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.*
import com.siro.mystrava.presentation.dashboard.widgets.*
import com.siro.mystrava.presentation.viewmodels.*

@Composable
fun HomeScreenWidgets1(
    viewModel: HomeViewModel,
    state: ActivityUiState.DataLoaded,
    selectedActivityType: ActivityType,
    selectedUnitType: UnitType,
    onWeeklySnapshotClick: () -> Unit,
) {
    var currentMonthMetrics by remember {
        mutableStateOf(SummaryMetrics(0, 0f, 0f, 0))
    }
    val updateMonthlyMetrics = { summaryMetrics: SummaryMetrics ->
        currentMonthMetrics = summaryMetrics
    }
    val saveWeeklyDistance = { weeklyDistance: String, weeklyElevation: String ->
        viewModel.saveWeeklyStats(weeklyDistance, weeklyElevation)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .background(color = MaterialTheme.colorScheme.surface)
    ) {
        MyStravaWidget(
            content = {
                WeekSummaryWidget(
                    weeklyDistanceMap = state.calendarActivities.weeklyDistanceMap,
                    currentWeeklyInfo = viewModel.calendarData.currentWeek,
                    isLoading = state.calendarActivities.lastTwoMonthsActivities.isEmpty(),
                    saveWeeklyStats = saveWeeklyDistance,
                    onClick = onWeeklySnapshotClick
                )
            },
            widgetName = "Week Summary"
        )

        MyStravaWidget(
            content = {
                MonthWidget(
                    monthlyWorkouts = state.calendarActivities.currentMonthActivities,
                    updateMonthlyMetrics = updateMonthlyMetrics,
                    selectedActivityType = selectedActivityType,
                    selectedUnitType = selectedUnitType,
                    monthWeekMap = viewModel.calendarData.monthWeekMap,
                    today = viewModel.calendarData.currentDayInt,
                    isLoading = state.calendarActivities.currentMonthActivities.isEmpty()
                )
            }, widgetName = "Month Summary"
        )

        MyStravaWidget(
            content = {
                WeekCompareWidget(
                    activitesList = state.calendarActivities.lastTwoMonthsActivities,
                    selectedActivityType = selectedActivityType,
                    selectedUnitType = selectedUnitType,
                    today = viewModel.calendarData.currentDayInt,
                    monthWeekMap = viewModel.calendarData.monthWeekMap,
                    isLoading = state.calendarActivities.lastTwoMonthsActivities.isEmpty()
                )
            }, widgetName = "Week vs Week"
        )

        MyStravaWidget(
            content = {
                CompareWidget(
                    dashboardType = DashboardType.Month,
                    selectedActivityType = selectedActivityType,
                    currentMonthMetrics = state.calendarActivities.monthlySummaryMetrics[0],
                    columnTitles = arrayOf(
                        viewModel.calendarData.currentMonth.second,
                        viewModel.calendarData.previousMonth.second,
                        viewModel.calendarData.twoMonthPrevious.second
                    ),
                    prevMetrics = state.calendarActivities.monthlySummaryMetrics[1],
                    prevPrevMetrics = state.calendarActivities.monthlySummaryMetrics[2],
                    selectedUnitType = selectedUnitType
                )
            }, widgetName = "Month vs Month"
        )

        MyStravaWidget(
            content = {
                YearWidget(
                    yearMetrics = state.calendarActivities.currentYearActivities.getStats(
                        selectedActivityType
                    ),
                    selectedActivityType = selectedActivityType,
                    selectedUnitType = selectedUnitType,
                    isLoading = state.calendarActivities.currentMonthActivities.isEmpty()
                )
            }, widgetName = "Year to Date"
        )

        MyStravaWidget(
            content = {
                val yearlySummaryMetrics by viewModel.yearlySummaryMetrics.observeAsState(
                    initial = emptyList()
                )
                if (yearlySummaryMetrics.isNotEmpty()) {
                    CompareWidget(
                        dashboardType = DashboardType.Year,
                        selectedActivityType = selectedActivityType,
                        columnTitles = arrayOf("2023", "2022", "2021"),
                        currentMonthMetrics = yearlySummaryMetrics[0],
                        prevMetrics = yearlySummaryMetrics[1],
                        prevPrevMetrics = yearlySummaryMetrics[2],
                        selectedUnitType = selectedUnitType
                    )
                }
            }, widgetName = "Year vs Year"
        )
    }
}
