package com.siro.mystrava.presentation.navigation

object Routes {
    const val Home = "home"
    const val Workout = "workout/{activityItem}"
    const val Export = "export/{activityId}"
    const val Settings = "settings"

    fun workoutRoute(activityItem: String): String {
        return "workout/$activityItem"
    }

    fun exportRoute(activityId: String): String {
        return "export/$activityId"
    }
}
