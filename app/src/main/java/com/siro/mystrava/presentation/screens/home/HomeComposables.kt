package com.siro.mystrava.presentation.screens.home

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import androidx.compose.ui.window.Dialog
import com.google.accompanist.swiperefresh.*
import com.siro.mystrava.R
import com.siro.mystrava.data.models.activites.ActivityItem
import com.siro.mystrava.presentation.theme.primaryColor
import com.siro.mystrava.presentation.viewmodels.*

/*@Composable
fun HomeScreenX(viewModel: HomeViewModel, paddingValues: PaddingValues) {
    var fetchData by rememberSaveable { mutableStateOf(0) }

    if (fetchData == 0) {
        viewModel.fetchData()
        fetchData = fetchData.inc()
    }

    val activityUiState by viewModel.activityUiState.collectAsState()

    val selectedActivityType by viewModel.activityType.observeAsState(ActivityType.Run)

    val selectedUnitType by viewModel.unitType.observeAsState(UnitType.Imperial)

    val preferredMeasureType by viewModel.measureType.observeAsState(initial = MeasureType.Absolute)

    var currentMonthMetrics by remember {
        mutableStateOf(SummaryMetrics(0, 0f, 0f, 0))
    }
    val updateMonthlyMetrics = { summaryMetrics: SummaryMetrics ->
        currentMonthMetrics = summaryMetrics
    }

    var refreshState = rememberSwipeRefreshState(false)

    val saveWeeklyDistance = { weeklyDistance: String, weeklyElevation: String ->
        viewModel.saveWeeklyStats(weeklyDistance, weeklyElevation)
    }

    var showWeeklyDetailSnapshot by remember { mutableStateOf(false) }

    val weeklySnapshotDetails by viewModel.weeklyActivityDetails.observeAsState(emptyList())

    Scaffold(
        topBar = {
            Text(
                "Streak",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center,
            )
        },
        content = {
            Box(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
                    .background(color = MaterialTheme.colorScheme.surface)
            ) {
                SwipeRefresh(
                    state = refreshState,
                    onRefresh = {
                        viewModel.fetchData()
                    },
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
                            Column(modifier = Modifier.fillMaxSize()) {

                            }
                        }

                        is ActivityUiState.DataLoaded -> {
                            refreshState.isRefreshing = false
                            Box() {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(paddingValues = paddingValues)
                                        .verticalScroll(rememberScrollState())
                                        .background(color = MaterialTheme.colorScheme.surface)
                                ) {

                                    StreakDashboardWidget(
                                        content = {
                                            WeekSummaryWidget(
                                                weeklyDistanceMap = state.calendarActivities.weeklyDistanceMap,
                                                currentWeeklyInfo = viewModel.calendarData.currentWeek,
                                                isLoading = state.calendarActivities.lastTwoMonthsActivities.isEmpty(),
                                                saveWeeklyStats = saveWeeklyDistance,
                                                onClick = {
                                                    viewModel.loadWeekActivityDetails(state.calendarActivities.weeklyActivityIds.map { activityId -> activityId.toString() })
                                                    showWeeklyDetailSnapshot = true
                                                }
                                            )
                                        },
                                        widgetName = "Week Summary"
                                    )

                                    StreakDashboardWidget(
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

                                    StreakDashboardWidget(
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

                                    StreakDashboardWidget(
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

                                    StreakDashboardWidget(
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

                                    StreakDashboardWidget(
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

                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(top = 16.dp, bottom = 16.dp),
                                        horizontalArrangement = Arrangement.Center
                                    ) {
                                        Image(
                                            painter = painterResource(id = R.drawable.powerd_by_strava_logo),
                                            contentDescription = "Powered By Strava",
                                        )
                                    }
                                }

                                if (showWeeklyDetailSnapshot) {
                                    Dialog(onDismissRequest = {
                                        showWeeklyDetailSnapshot = false
                                    }) {
                                        Column(
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            modifier = Modifier
                                                .height(200.dp)
                                                .fillMaxWidth()
                                                .clip(RoundedCornerShape(20))
                                                .padding(16.dp)
                                                .background(MaterialTheme.colorScheme.primaryContainer),
                                            verticalArrangement = Arrangement.Center
                                        ) {
                                            Text(
                                                "Weekly Details",
                                                style = MaterialTheme.typography.headlineMedium,
                                                modifier = Modifier.padding(bottom = 16.dp)
                                            )

                                            var totalCalories = 0.0
                                            var avgTempo = 0.0
                                            var bpm = 0.0

                                            weeklySnapshotDetails.forEach {
                                                totalCalories += it.calories
                                                avgTempo += it.average_cadence
                                                bpm += it.average_heartrate
                                            }


                                            Row(horizontalArrangement = Arrangement.SpaceBetween) {

                                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                                    Text("Total Cal")
                                                    Text("$totalCalories cal")
                                                }

                                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                                    Text("Avg Cadence")
                                                    Text(
                                                        "${
                                                            avgTempo.div(weeklySnapshotDetails.size)
                                                                .toInt()
                                                        }"
                                                    )
                                                }

                                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                                    Text("Avg Bpm")
                                                    Text("${bpm.div(weeklySnapshotDetails.size)} bpm")
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        else -> {}
                    }
                }
            }
        }
    )
}

@Composable
fun ColumnScope.Title(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.headlineMedium,
        fontSize = 18.sp,
        color = MaterialTheme.colorScheme.onSurface,
        modifier = Modifier.padding(top = 4.dp, start = 16.dp)
    )
}


@Composable
fun StreakDashboardWidget(content: @Composable () -> Unit, widgetName: String) {
    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = widgetName,
                style = MaterialTheme.typography.headlineMedium,
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .padding(top = 4.dp, start = 16.dp)
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            content()
        }
    }
}

enum class StatType { Distance, Time, Elevation, Count, Pace }

data class SummaryMetrics(
    val count: Int = 0,
    val totalDistance: Float = 0f,
    val totalElevation: Float = 0f,
    val totalTime: Int = 0
)

fun List<ActivityItem>.getStats(selectedActivity: ActivityType): SummaryMetrics {
    val filteredActivities =
        if (selectedActivity == ActivityType.All) this
        else this.filter { it.type == selectedActivity.name }

    var count = 0
    var distance = 0f
    var elevation = 0f
    var time = 0

    filteredActivities.forEach {
        count = count.inc()
        distance += it.distance
        elevation += it.total_elevation_gain
        time += it.moving_time
    }

    return SummaryMetrics(
        count = count,
        totalDistance = distance,
        totalTime = time,
        totalElevation = elevation
    )
}*/