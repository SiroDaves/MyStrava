package com.siro.mystrava.domain.repositories

import com.siro.mystrava.data.models.profile.*
import kotlinx.coroutines.flow.SharedFlow

interface SettingsRepository {
    val widgetStatus: SharedFlow<Boolean>

    suspend fun authAthlete(code: String)

    suspend fun fetchAthlete(): StravaAthlete?

    suspend fun fetchAthleteStats(id: String): AthleteStats?
}

