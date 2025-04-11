package com.siro.mystrava.presentation.screens.workout.widgets

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.dp
import com.siro.mystrava.data.models.activites.ActivityItem
import com.siro.mystrava.data.models.detail.ActivityDetail
import com.siro.mystrava.presentation.viewmodels.WorkoutViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutEditForm(
    activity: ActivityItem,
    details: ActivityDetail,
    viewModel: WorkoutViewModel,
) {
    var title by remember { mutableStateOf(details.name) }
    var description by remember { mutableStateOf(details.description) }
    var type by remember { mutableStateOf(details.type) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TextField(
            value = title,
            onValueChange = { title = it },
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
                .weight(1f)
        )

        Spacer(Modifier.height(8.dp))

        TextField(
            value = type,
            onValueChange = { type = it },
            label = { Text("Activity Type") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = {
                viewModel.updateActivity(activity)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text("Save")
        }
    }
}

