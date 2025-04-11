package com.siro.mystrava.presentation.navigation

object Routes {
    const val HOME = "home"
    const val WORKOUT = "workout/{activityId}"

    fun workoutRoute(activityId: String): String {
        return "workout/$activityId"
    }
}