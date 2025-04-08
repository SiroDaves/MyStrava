package com.siro.mystrava.presentation.home

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.siro.mystrava.data.models.activites.ActivityItem
import com.siro.mystrava.data.models.detail.ActivityDetail

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

@Composable
fun Workout(activity: ActivityItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp)
        ) {
            Text(
                text = activity.type,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Text(
                text = activity.type,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Text(
                text = activity.distance.toString(),
                fontSize = 14.sp,
                //color = Color.Gray
            )
        }
    }
}
