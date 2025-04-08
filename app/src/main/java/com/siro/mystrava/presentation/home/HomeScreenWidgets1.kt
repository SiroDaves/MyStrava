package com.siro.mystrava.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.*
import com.siro.mystrava.presentation.home.widgets.*
import com.siro.mystrava.presentation.viewmodels.*

@OptIn(ExperimentalPagerApi::class)
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

    val pagerState = rememberPagerState()

    val widgets = listOf<@Composable () -> Unit>(
        {
            MyStravaWidget(
                content = {
                    WeekSummaryWidget(
                        weeklyDistanceMap = state.calendarActivities.weeklyDistanceMap,
                        currentWeeklyInfo = viewModel.calendarData.currentWeek,
                        isLoading = state.calendarActivities.lastTwoMonthsActivities.isEmpty(),
                        saveWeeklyStats = { d, e -> viewModel.saveWeeklyStats(d, e) },
                        onClick = onWeeklySnapshotClick
                    )
                },
                widgetName = "Week Summary"
            )
        },
        {
            MyStravaWidget(
                content = {
                    MonthWidget(
                        monthlyWorkouts = state.calendarActivities.currentMonthActivities,
                        updateMonthlyMetrics = { currentMonthMetrics = it },
                        selectedActivityType = selectedActivityType,
                        selectedUnitType = selectedUnitType,
                        monthWeekMap = viewModel.calendarData.monthWeekMap,
                        today = viewModel.calendarData.currentDayInt,
                        isLoading = state.calendarActivities.currentMonthActivities.isEmpty()
                    )
                }, widgetName = "Month Summary"
            )
        },
        {
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
        },
        {
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
        },
        {
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
        }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        HorizontalPager(
            count = widgets.size,
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp) // adjust based on your content
        ) { page ->
            widgets[page]()
        }

        HorizontalPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(16.dp)
        )
    }
}