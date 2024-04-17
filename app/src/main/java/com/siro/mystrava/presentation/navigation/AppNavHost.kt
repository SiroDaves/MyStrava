package com.siro.mystrava.presentation.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.*
import androidx.navigation.compose.*
import com.google.gson.Gson
import com.siro.mystrava.presentation.screens.home.HomeScreen
import com.siro.mystrava.presentation.screens.settings.SettingsScreen
import com.siro.mystrava.presentation.screens.upload.UploadScreen
import com.siro.mystrava.presentation.screens.workout.WorkoutScreen
import com.siro.mystrava.presentation.viewmodels.*

@OptIn(ExperimentalComposeUiApi::class, ExperimentalFoundationApi::class)
@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController(),
    homeViewModel: HomeViewModel,
) {
    NavHost(
        navController = navController,
        startDestination = Routes.HOME
    ) {

        composable(Routes.HOME) {
            HomeScreen(
                viewModel = homeViewModel,
                navController = navController,
                onItemClick = { selectedActivityItem ->
                    val itemJson = Gson().toJson(selectedActivityItem)
                    navController.navigate(Routes.workoutRoute(itemJson))
                }
            )
        }

        composable(Routes.UPLOAD) {
            val uploadViewModel: UploadViewModel = hiltViewModel()
            UploadScreen(
                viewModel = uploadViewModel,
                onBackPressed = { navController.popBackStack() },
            )
        }

        composable(
            route = Routes.WORKOUT,
            arguments = listOf(
                navArgument("activityItem") { type = NavType.StringType },
            )
        ) { backStackEntry ->
            val activityItem = backStackEntry.arguments?.getString("activityItem") ?: ""
            val workoutViewModel: WorkoutViewModel = hiltViewModel()

            WorkoutScreen(
                viewModel = workoutViewModel,
                activityItem = activityItem,
                onBackPressed = { navController.popBackStack() },
            )
        }

        composable(Routes.SETTINGS) {
            SettingsScreen(
                viewModel = homeViewModel,
                selectedActivityType = TODO(),
                selectedUnitType = TODO(),
                selectedMeasureType = TODO(),
            )
        }

    }
}