package com.siro.mystrava.presentation.screens.home.widgets

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import com.siro.mystrava.data.models.activites.ActivityItem
import com.siro.mystrava.presentation.viewmodels.ActivityType

@Composable
fun MyStravaWidget(content: @Composable () -> Unit, widgetName: String) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 15.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
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
}

@Preview(showBackground = true)
@Composable
fun WorkoutPreview() {
    val sampleActivity = ActivityItem(
        activityId = 1,
        id = 123456L,
        type = "Run",
        name = "Morning Run",
        distance = 5702.7f,
        calories = 320.5,
        elapsed_time = 1800,
        moving_time = 2272,
        start_date = "2023-04-09T08:30:00Z",
        start_date_local = "2023-04-09T08:30:00Z",
        total_elevation_gain = 125f,
        comment_count = 3,
        kudos_count = 15,
        achievement_count = 2,
        average_speed = 0.42,
        average_heartrate = 161.0
    )

    Workout(activity = sampleActivity)
}
