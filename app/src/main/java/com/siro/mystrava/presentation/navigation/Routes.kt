package com.siro.mystrava.presentation.navigation

object Routes {
    const val HOME = "home"
    const val WORKOUT = "workout/{activityItem}"
    const val UPLOAD = "upload"
    const val SETTINGS = "settings"

    fun workoutRoute(activityItem: String): String {
        return "workout/$activityItem"
    }

    fun uploadRoute(): String {
        return "export"
    }
}
