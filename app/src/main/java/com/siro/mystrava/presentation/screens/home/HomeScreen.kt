package com.siro.mystrava.presentation.screens.home

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import com.google.accompanist.swiperefresh.*
import com.siro.mystrava.presentation.screens.home.widgets.*
import com.siro.mystrava.presentation.theme.primaryColor
import com.siro.mystrava.presentation.viewmodels.*

@Composable
fun HomeScreen( viewModel: HomeViewModel) {
    var fetchData by rememberSaveable { mutableStateOf(0) }
    if (fetchData == 0) {
        viewModel.fetchData()
        fetchData = fetchData.inc()
    }

    val activityUiState by viewModel.activityUiState.collectAsState()
    val selectedActivityType by viewModel.activityType.observeAsState(ActivityType.Run)
    val selectedUnitType by viewModel.unitType.observeAsState(UnitType.Imperial)
    val refreshState = rememberSwipeRefreshState(false)

    Scaffold(
        topBar = {
            Text(
                "My Strava",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.surface)
        ) {
            SwipeRefresh(
                state = refreshState,
                onRefresh = viewModel::fetchData,
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
                    is ActivityUiState.Error -> ErrorState(state)
                    is ActivityUiState.Loading -> LoadingState(refreshState)
                    is ActivityUiState.DataLoaded -> HomeContent()
                }
            }
        }
    }
}

@Composable
private fun HomeContent() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {

    }
}
