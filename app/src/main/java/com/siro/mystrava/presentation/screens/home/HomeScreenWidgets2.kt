package com.siro.mystrava.presentation.screens.home

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.siro.mystrava.data.models.activites.ActivityItem
import com.siro.mystrava.data.models.detail.ActivityDetail
import com.siro.mystrava.presentation.screens.home.widgets.Workout

@Composable
fun HomeScreenWidgets2(
    weeklySnapshotDetails: List<ActivityDetail>,
) {

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
        achievement_count = 2
    )

    Workout(activity = sampleActivity)
}
