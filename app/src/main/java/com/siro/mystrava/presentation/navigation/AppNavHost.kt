package com.siro.mystrava.presentation.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.*
import androidx.navigation.compose.*
import com.siro.mystrava.presentation.screens.home.HomeScreen
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
        startDestination = Routes.HOME
    ) {
        composable(Routes.HOME) {
            HomeScreen(
                viewModel = homeViewModel,
                onItemClick = { item ->
                    navController.navigate(Routes.workoutRoute(item.id.toString()))
                }
            )
        }

        composable(
            route = Routes.WORKOUT,
            arguments = listOf(navArgument("activityId") { type = NavType.StringType })
        ) { backStackEntry ->
            val activityId = backStackEntry.arguments?.getString("activityId") ?: ""
            val workoutViewModel: WorkoutViewModel = hiltViewModel()

            WorkoutScreen(
                viewModel = workoutViewModel,
                activityId = activityId,
                onBackPressed = { navController.popBackStack() }
            )
        }
    }
}