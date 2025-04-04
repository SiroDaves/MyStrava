package com.siro.mystrava

import android.os.*
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.*
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.*
import com.siro.mystrava.presentation.screens.auth.WebAuthView
import com.siro.mystrava.presentation.screens.dashboard.Dashboard
import com.siro.mystrava.presentation.viewmodels.*
import com.siro.mystrava.presentation.viewmodels.UnitType
import com.siro.mystrava.presentation.screens.settings.StreakSettingsView
import com.siro.mystrava.presentation.theme.*
import com.siro.mystrava.presentation.viewmodels.DashboardViewModel
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalComposeUiApi
@ExperimentalFoundationApi
@Keep
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: DashboardViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()

            Material3Theme(content = {
                val isLoggedIn by viewModel.isLoggedInStrava.observeAsState()
                var showLoginDialog by remember { mutableStateOf(false) }
                var selectedTab by remember { mutableStateOf(0) }
                val selectedActivityType by viewModel.activityType.observeAsState(ActivityType.Run)
                val selectedUnitType by viewModel.unitType.observeAsState(UnitType.Imperial)
                val selectedMeasureType by viewModel.measureType.observeAsState(MeasureType.Absolute)

                isLoggedIn?.let {
                    if (it) {
                        Scaffold(
                            content = { paddingValues ->
                                NavHost(
                                    navController,
                                    startDestination = NavigationDestination.StravaDashboard.destination
                                ) {
                                    composable(NavigationDestination.StravaDashboard.destination) {
                                        Dashboard(
                                            viewModel = viewModel,
                                            paddingValues = paddingValues
                                        )
                                    }
                                    composable(NavigationDestination.StreakSettings.destination) {
                                        StreakSettingsView(
                                            viewModel = viewModel,
                                            selectedActivityType = selectedActivityType,
                                            selectedUnitType = selectedUnitType,
                                            selectedMeasureType = selectedMeasureType
                                        )
                                    }
                                }
                            },
                            bottomBar = {
                                NavigationBar(
                                    containerColor = MaterialTheme.colorScheme.primaryContainer
                                ) {
                                    val tintColor = MaterialTheme.colorScheme.onSurface

                                    NavigationBarItem(
                                        selected = selectedTab == 0,
                                        onClick = {
                                            selectedTab = 0
                                            navController.navigate(NavigationDestination.StravaDashboard.destination) {
                                                popUpTo(navController.graph.findStartDestination().id) {
                                                    saveState = true
                                                }
                                                launchSingleTop = true
                                                restoreState = true
                                            }
                                        },
                                        icon = {
                                            Icon(
                                                imageVector = Icons.Default.Home,
                                                contentDescription = "",
                                                tint = if (selectedTab == 0) tintColor else tintColor.copy(.7f)
                                            )
                                        },
                                        label = {
                                            Text(
                                                "Dashboard",
                                                color = if (selectedTab == 0) tintColor else tintColor.copy(.7f)
                                            )
                                        }
                                    )
                                    NavigationBarItem(
                                        selected = selectedTab == 2,
                                        onClick = {
                                            selectedTab = 2
                                            navController.navigate(NavigationDestination.StreakSettings.destination) {
                                                popUpTo(navController.graph.findStartDestination().id) {
                                                    saveState = true
                                                }
                                                launchSingleTop = true
                                                restoreState = true
                                            }
                                        },
                                        icon = {
                                            Icon(
                                                imageVector = Icons.Default.Settings,
                                                contentDescription = "",
                                                tint = if (selectedTab == 2) tintColor else tintColor.copy(.7f)
                                            )
                                        },
                                        label = {
                                            Text(
                                                "Settings",
                                                color = if (selectedTab == 2) tintColor else tintColor.copy(.7f)
                                            )
                                        }
                                    )
                                }
                            }
                        )
                    } else {
                        Scaffold(
                            containerColor = primaryColorShade1,
                            contentColor = MaterialTheme.colorScheme.onSurface,
                            content = { paddingValues ->
                                Column(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(16.dp)
                                ) {
                                    Text(
                                        text = "Welcome to My Strava!",
                                        style = MaterialTheme.typography.headlineMedium,
                                        modifier = Modifier.padding(vertical = 16.dp)
                                    )
                                    Box(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .padding(16.dp)
                                            .padding(paddingValues = paddingValues),
                                        contentAlignment = Alignment.BottomCenter
                                    ) {
                                        val width = with(LocalDensity.current) { 772f.toDp() }
                                        val height = with(LocalDensity.current) { 192f.toDp() }
                                        Image(
                                            painter = painterResource(id = R.drawable.connect_with_strava),
                                            contentDescription = "Connect to strava",
                                            modifier = Modifier
                                                .size(width = width, height = height)
                                                .clickable {
                                                    showLoginDialog = !showLoginDialog
                                                })
                                    }
                                }

                                if (showLoginDialog) {
                                    val onFinish = { showLoginDialog = !showLoginDialog }
                                    Dialog(onDismissRequest = {
                                        showLoginDialog = !showLoginDialog
                                    }) {
                                        WebAuthView(
                                            viewModel = viewModel,
                                            onFinish = onFinish
                                        )
                                    }
                                }

                            }
                        )
                    }
                }
            })
        }
    }
}

@Keep
sealed class NavigationDestination(
    val destination: String,
    val label: String? = null,
    val resId: Int? = null,
) {
    object StravaDashboard :
        NavigationDestination("dashboard", "Dashboard", R.drawable.ic_dash)

    object StreakSettings :
        NavigationDestination("settings", "Settings", R.drawable.ic_settings)
}