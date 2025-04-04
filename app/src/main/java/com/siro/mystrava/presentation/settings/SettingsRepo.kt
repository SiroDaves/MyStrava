package com.siro.mystrava.presentation.settings

import com.siro.mystrava.strava.model.profile.AthleteStats
import com.siro.mystrava.strava.model.profile.StravaAthlete
import kotlinx.coroutines.flow.SharedFlow

interface SettingsRepo {
    val widgetStatus: SharedFlow<Boolean>

    suspend fun authAthlete(code: String)

    suspend fun fetchAthlete(): StravaAthlete?

    suspend fun fetchAthleteStats(id: String): AthleteStats?
}