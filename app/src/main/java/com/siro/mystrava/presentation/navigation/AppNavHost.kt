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
import com.siro.mystrava.presentation.screens.workout.WorkoutScreen
import com.siro.mystrava.presentation.viewmodels.*

@OptIn(ExperimentalComposeUiApi::class, ExperimentalFoundationApi::class)
@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController(),
    homeViewModel: HomeViewModel
) {
    NavHost(
        navController = navController,
        startDestination = Routes.Home
    ) {

        composable(Routes.Home) {
            HomeScreen(
                viewModel = homeViewModel,
                onItemClick = { selectedActivityItem ->
                    val itemJson = Gson().toJson(selectedActivityItem)
                    navController.navigate(Routes.workoutRoute(itemJson))
                }
            )
        }

        composable(
            route = Routes.Workout,
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

        composable(
            route = Routes.Export,
            arguments = listOf(
                navArgument("activityId") { type = NavType.StringType },
            )
        ) { backStackEntry ->
            val activityId = backStackEntry.arguments?.getString("activityId") ?: ""
            val workoutViewModel: WorkoutViewModel = hiltViewModel()

            WorkoutScreen(
                viewModel = workoutViewModel,
                activityItem = activityId,
                onBackPressed = { navController.popBackStack() }
            )
        }

        composable(Routes.Settings) {
            SettingsScreen(
                viewModel = homeViewModel,
                selectedActivityType = TODO(),
                selectedUnitType = TODO(),
                selectedMeasureType = TODO(),
            )
        }

    }
}