package com.siro.mystrava.presentation.screens.workout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.pullrefresh.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.*
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.gson.Gson
import com.siro.mystrava.data.models.activites.ActivityItem
import com.siro.mystrava.presentation.screens.workout.widgets.*
import com.siro.mystrava.presentation.theme.primaryColor
import com.siro.mystrava.presentation.viewmodels.*
import com.siro.mystrava.presentation.widgets.*

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun WorkoutScreen(
    viewModel: WorkoutViewModel,
    activityItem: String,
    onBackPressed: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()
    val activityDetail by viewModel.activity.collectAsState()
    val activity = Gson().fromJson(activityItem, ActivityItem::class.java)
    var fetchData by rememberSaveable { mutableStateOf(0) }

    fun fetchData() {
        viewModel.fetchActivityDetails(activity.id.toString())
    }

    if (fetchData == 0) {
        fetchData()
        fetchData = fetchData.inc()
    }

    val isRefreshing = uiState is WorkoutUiState.Loading
    val pullRefreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = { fetchData() }
    )

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier
                    .padding(10.dp)
                    .shadow(
                        elevation = 5.dp,
                        spotColor = Color.DarkGray,
                        shape = RoundedCornerShape(10.dp)
                    ),
                title = { Text("${activity.type} Details") },
                navigationIcon = {
                    IconButton(onClick = onBackPressed) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Localized description"
                        )
                    }
                },
                actions = {
                    if (uiState == WorkoutUiState.Editing) {
                        IconButton(onClick = { viewModel.setLoaded() }) {
                            Icon(
                                imageVector = Icons.Filled.Close,
                                contentDescription = "Localized description"
                            )
                        }
                    } else {
                        IconButton(onClick = { viewModel.setEditing() }) {
                            Icon(
                                imageVector = Icons.Filled.Create,
                                contentDescription = "Localized description"
                            )
                        }
                    }
                },
            )
        },
        content = {
            Box(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
                    .background(color = MaterialTheme.colorScheme.surface)
            ) {
                /*if (showExportedDialog) {
                    DoneExportingDialog(
                        onDismissRequest = {
                            showExportedDialog = false
                        },
                        onConfirmation = {
                            showExportedDialog = false
                        }
                    )
                }*/
                when (uiState) {
                    is WorkoutUiState.Loading -> LoadingState("Loading data ...")
                    is WorkoutUiState.Saving -> LoadingState("Saving data ...")
                    is WorkoutUiState.Exporting -> LoadingState("Exporting data ...")

                    is WorkoutUiState.Success -> TODO()

                    is WorkoutUiState.Error -> {
                        val errorMessage = (uiState as WorkoutUiState.Error).errorMessage
                        ErrorState(
                            errorMessage = errorMessage,
                            onRetry = { fetchData() }
                        )
                    }

                    is WorkoutUiState.Loaded -> {
                        activityDetail?.let { activity ->
                            WorkoutContent(activity = activity)
                        } ?: run {
                            ErrorState(
                                errorMessage = "Activity details not found",
                                onRetry = { fetchData() }
                            )
                        }
                    }

                    is WorkoutUiState.Editing -> {
                        activityDetail?.let { details ->
                            WorkoutEditForm(activity, details, viewModel)
                        } ?: run {
                            ErrorState(
                                errorMessage = "Activity can not be edited",
                                onRetry = { fetchData() }
                            )
                        }
                    }

                    WorkoutUiState.Exported -> DoneExportingDialog(
                        onDismissRequest = { viewModel.setLoaded() },
                        onConfirmation = { viewModel.setEditing() },
                    )
                }
                PullRefreshIndicator(
                    refreshing = isRefreshing,
                    state = pullRefreshState,
                    modifier = Modifier.align(Alignment.TopCenter),
                    contentColor = primaryColor,
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.fetchActivityStream(activity.id.toString())
                },
            ) {
                Icon(Icons.Filled.KeyboardArrowDown, "Export")
            }
        }
    )
}
