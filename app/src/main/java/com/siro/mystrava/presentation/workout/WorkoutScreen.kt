package com.siro.mystrava.presentation.workout

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.siro.mystrava.presentation.viewmodels.WorkoutViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutScreen(
    viewModel: WorkoutViewModel,
    activityId: String,
    onBackPressed: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(activityId) {
        viewModel.fetchActivityDetails(activityId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Workout Details") },
                /*navigationIcon = {
                    IconButton(onClick = onBackPressed) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }*/
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
        ) {
            /*when (val state = activityState) {
                is WorkoutDetailState.Loading -> {
                    LoadingState()
                }
                is WorkoutDetailState.Error -> {
                    ErrorState(
                        errorMessage = state.message,
                        onRetry = { viewModel.fetchActivityDetails(activityId) }
                    )
                }
                is WorkoutDetailState.Success -> {
                    WorkoutDetailContent(activity = state.activity)
                }
            }*/
        }
    }
}
