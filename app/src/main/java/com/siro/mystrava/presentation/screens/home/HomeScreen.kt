package com.siro.mystrava.presentation.screens.home

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import com.google.accompanist.swiperefresh.*
import com.siro.mystrava.data.models.activites.ActivityItem
import com.siro.mystrava.presentation.screens.home.widgets.*
import com.siro.mystrava.presentation.theme.primaryColor
import com.siro.mystrava.presentation.viewmodels.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: HomeViewModel) {
    val activities by viewModel.activities.collectAsState(initial = emptyList())
    val loading by viewModel.loading.collectAsState(initial = false)
    val error by viewModel.error.collectAsState(initial = null)
    val refreshState = rememberSwipeRefreshState(isRefreshing = loading)

    LaunchedEffect(Unit) {
        viewModel.fetchData()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text("My Strava")
                }
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
                when {
                    error != null -> ErrorState(
                        errorMessage = error!!,
                        onRetry = { viewModel.fetchData() }
                    )

                    activities.isEmpty() && !loading -> EmptyState()

                    else -> LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(activities) { activity ->
                            Workout(activity = activity)
                        }
                    }
                }
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
                text = activity.distance.toString(),
                fontSize = 14.sp,
                //color = Color.Gray
            )
        }
    }
}
