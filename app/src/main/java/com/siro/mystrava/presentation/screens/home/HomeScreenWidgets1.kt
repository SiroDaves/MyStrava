package com.siro.mystrava.presentation.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.*
import com.siro.mystrava.presentation.home.widgets.*
import com.siro.mystrava.presentation.screens.home.widgets.CompareWidget
import com.siro.mystrava.presentation.screens.home.widgets.DashboardType
import com.siro.mystrava.presentation.screens.home.widgets.MonthWidget
import com.siro.mystrava.presentation.screens.home.widgets.MyStravaWidget
import com.siro.mystrava.presentation.screens.home.widgets.SummaryMetrics
import com.siro.mystrava.presentation.screens.home.widgets.WeekCompareWidget
import com.siro.mystrava.presentation.screens.home.widgets.WeekSummaryWidget
import com.siro.mystrava.presentation.screens.home.widgets.YearWidget
import com.siro.mystrava.presentation.screens.home.widgets.getStats
import com.siro.mystrava.presentation.viewmodels.*
import kotlin.collections.get

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
                widgetName = "Week Summary",
                content = {
                    WeekSummaryWidget(
                        weeklyDistanceMap = state.calendarActivities.weeklyDistanceMap,
                        currentWeeklyInfo = viewModel.calendarData.currentWeek,
                        isLoading = state.calendarActivities.lastTwoMonthsActivities.isEmpty(),
                        saveWeeklyStats = { d, e -> viewModel.saveWeeklyStats(d, e) },
                        onClick = onWeeklySnapshotClick
                    )
                },
            )
        },
        {
            MyStravaWidget(
                widgetName = "Month Summary",
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
                },
            )
        },
        {
            MyStravaWidget(
                widgetName = "Week vs Week",
                content = {
                    WeekCompareWidget(
                        activitesList = state.calendarActivities.lastTwoMonthsActivities,
                        selectedActivityType = selectedActivityType,
                        selectedUnitType = selectedUnitType,
                        today = viewModel.calendarData.currentDayInt,
                        monthWeekMap = viewModel.calendarData.monthWeekMap,
                        isLoading = state.calendarActivities.lastTwoMonthsActivities.isEmpty()
                    )
                },
            )
        },
        {
            MyStravaWidget(
                widgetName = "Month vs Month",
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
                },
            )
        },
        {
            MyStravaWidget(
                widgetName = "Year to Date",
                content = {
                    YearWidget(
                        yearMetrics = state.calendarActivities.currentYearActivities.getStats(
                            selectedActivityType
                        ),
                        selectedActivityType = selectedActivityType,
                        selectedUnitType = selectedUnitType,
                        isLoading = state.calendarActivities.currentMonthActivities.isEmpty()
                    )
                },
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
                .height(250.dp)
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