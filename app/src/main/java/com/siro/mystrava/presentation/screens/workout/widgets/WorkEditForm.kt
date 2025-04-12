package com.siro.mystrava.presentation.screens.workout.widgets

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.dp
import com.siro.mystrava.data.models.activites.*
import com.siro.mystrava.data.models.detail.ActivityDetail
import com.siro.mystrava.presentation.viewmodels.WorkoutViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutEditForm(
    activity: ActivityItem,
    details: ActivityDetail,
    viewModel: WorkoutViewModel,
) {
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var type by remember { mutableStateOf("") }
    var workoutType by remember { mutableStateOf("") }

    LaunchedEffect(details) {
        name = details.name.orEmpty()
        description = details.description.orEmpty()
        type = details.type.orEmpty()
        workoutType = details.type.orEmpty()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Title") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        TextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description") },
            modifier = Modifier
                .fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        TextField(
            value = type,
            onValueChange = { type = it },
            label = { Text("Activity Type") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(16.dp))

        TextField(
            value = workoutType,
            onValueChange = { workoutType = it },
            label = { Text("WorkOut Type") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = {
                val apiActivity = ActivityUpdate(
                    id = activity.id,
                    name = name,
                    description = description,
                    type = type,
                    sport_type = type,
                    workout_type = workoutType,
                )
                val dbActivity = activity.copy(
                    name = name,
                    type = type,
                )
                viewModel.updateActivity(dbActivity, apiActivity)
                viewModel.fetchActivityDetails(activity.id.toString())
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text("Save Details")
        }
    }
}