package com.siro.mystrava.presentation.navigation

object Routes {
    const val Home = "home"
    const val Workout = "workout/{activityItem}"
    const val Upload = "upload"
    const val Settings = "settings"

    fun workoutRoute(activityItem: String): String {
        return "workout/$activityItem"
    }

    fun uploadRoute(): String {
        return "export"
    }
}
