package com.siro.mystrava.domain.repositories

import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import android.util.Log
import com.siro.mystrava.R
import com.siro.mystrava.data.models.profile.*
import com.siro.mystrava.data.sources.remote.AthleteApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    private val sessionRepo: SessionRepository,
    private val athleteApi: AthleteApi,
    val context: Context,
) : SettingsRepository, OnSharedPreferenceChangeListener {

    private val preferences: SharedPreferences = context.getSharedPreferences(
        context.getString(R.string.preference_file_key),
        Context.MODE_PRIVATE
    )

    private val _widgetStatus = MutableSharedFlow<Boolean>(replay = 0)
    override val widgetStatus: SharedFlow<Boolean> = _widgetStatus

    fun refreshPrefs(): Boolean {
        return preferences.getBoolean("widgetEnable", false)
    }

    init {
        preferences.registerOnSharedPreferenceChangeListener(this)
        _widgetStatus.tryEmit(refreshPrefs())
    }

    override suspend fun authAthlete(code: String) {
        withContext(Dispatchers.IO) {
            sessionRepo.getFirstTokens(code)
        }
    }

    override suspend fun fetchAthlete(): StravaAthlete? = withContext(Dispatchers.IO) {
        val request = athleteApi.getAthlete()
        request
    }

    override suspend fun fetchAthleteStats(id: String): AthleteStats? =
        withContext(Dispatchers.IO) {
            athleteApi.getAthleteStats(id)
        }

    override fun onSharedPreferenceChanged(preferences: SharedPreferences?, key: String?) {
        if (key == "widgetEnabled") {
            val widgetEnabled = preferences?.getBoolean("widgetEnable", false)
            widgetEnabled?.let {
                if (it) {
                    Log.d("TAG", "onSharedPreferenceChanged: $it")
                    _widgetStatus.tryEmit(refreshPrefs())
                }
            }
        }
    }
}