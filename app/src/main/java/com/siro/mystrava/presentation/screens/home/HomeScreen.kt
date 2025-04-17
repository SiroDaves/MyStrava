package com.siro.mystrava.presentation.screens.home

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.pullrefresh.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.*
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.siro.mystrava.data.models.activites.ActivityItem
import com.siro.mystrava.presentation.navigation.Routes
import com.siro.mystrava.presentation.widgets.*
import com.siro.mystrava.presentation.screens.home.widgets.Workout
import com.siro.mystrava.presentation.theme.primaryColor
import com.siro.mystrava.presentation.viewmodels.*

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@ExperimentalComposeUiApi
@ExperimentalFoundationApi
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onItemClick: (ActivityItem) -> Unit,
) {
    var fetchData by rememberSaveable { mutableStateOf(0) }
    val navController = rememberNavController()

    if (fetchData == 0) {
        viewModel.fetchData()
        fetchData = fetchData.inc()
    }

    val activities by viewModel.activities.collectAsState(initial = emptyList())
    val uiState by viewModel.uiState.collectAsState()

    val isRefreshing = uiState is HomeUiState.Loading
    val pullRefreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = { viewModel.fetchData() }
    )

    Scaffold(
        topBar = {
            Surface(shadowElevation = 3.dp) {
                TopAppBar(
                    title = { Text("My Strava") },
                    actions = {
                        IconButton(onClick = { }) {
                            Icon(
                                imageVector = Icons.Filled.Settings,
                                contentDescription = "Settings"
                            )
                        }
                    },
                )
            }
        },
        content = {
            Box(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
                    .background(color = MaterialTheme.colorScheme.surface)
            ) {
                when (val state = uiState) {
                    is HomeUiState.Error -> {
                        ErrorState(
                            errorMessage = state.errorMessage,
                            onRetry = { viewModel.fetchData() }
                        )
                    }

                    is HomeUiState.Loading -> {
                        LoadingState("Loading data ...")
                    }

                    is HomeUiState.Loaded -> {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(10.dp),
                            contentPadding = PaddingValues(horizontal = 10.dp)
                        ) {
                            items(activities) { activity ->
                                Workout(
                                    activity = activity,
                                    onActivityClick = { clickedItem -> onItemClick(clickedItem) },
                                )
                            }
                        }
                    }
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
                onClick = { navController.navigate(Routes.UPLOAD) }
            ) {
                Icon(Icons.Filled.Add, "Add")
            }
        }
    )
}