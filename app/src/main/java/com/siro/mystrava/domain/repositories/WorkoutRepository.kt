package com.siro.mystrava.domain.repositories

import android.content.*
import android.util.Log
import com.siro.mystrava.R
import com.siro.mystrava.data.sources.remote.ActivitiesApi
import com.siro.mystrava.data.sources.local.ActivitiesDao
import com.siro.mystrava.domain.entities.*
import com.siro.mystrava.data.models.activites.ActivityItem
import com.siro.mystrava.data.models.detail.ActivityDetail
import com.siro.mystrava.data.sources.local.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.*
import javax.inject.*
import java.time.*

@Singleton
class WorkoutRepository @Inject constructor(
    val context: Context,
    private val activitiesApi: ActivitiesApi
) : SharedPreferences.OnSharedPreferenceChangeListener {


    private val preferences: SharedPreferences = context.getSharedPreferences(
        context.getString(R.string.preference_file_key),
        Context.MODE_PRIVATE
    )

    private var activitiesDao: ActivitiesDao?

    private val _widgetStatus = MutableSharedFlow<Boolean>(replay = 0)
    val widgetStatus: SharedFlow<Boolean> = _widgetStatus

    fun refreshPrefs(): Boolean {
        return preferences.getBoolean("widgetEnable", false)
    }

    init {
        val db = AppDatabase.getDatabase(context)
        activitiesDao = db?.activitiesDao()

        preferences.registerOnSharedPreferenceChangeListener(this)
        _widgetStatus.tryEmit(refreshPrefs())
    }

    suspend fun loadActivityDetail(activityId: String): ActivityDetail {
        return withContext(Dispatchers.IO) {
            activitiesApi.getActivityDetail(activityId)
        }
    }

    suspend fun saveActivty(ActivityItem: ActivityItem) {
        withContext(Dispatchers.IO) {
            activitiesDao?.insertAll(ActivityItem)
        }
    }

    fun savePreferredActivity(activityType: ActivityType) {
        preferences.edit().putString(activityTypeKey, activityType.name).apply()
    }

    fun savePreferredUnits(unitType: UnitType) {
        preferences.edit().putString(unitTypeKey, unitType.name).apply()
    }

    fun saveMeasureType(measureType: MeasureType) {
        preferences.edit().putString(Companion.measureType, measureType.name).apply()
    }

    fun fetchLastUpdatedTime(): LocalDateTime? {
        val lastUpdatedString = preferences.getString(lastUpdatedKey, "")
        return if (lastUpdatedString.isNullOrEmpty())
            null
        else
            LocalDateTime.parse(lastUpdatedString)
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

    companion object {
        const val activityTypeKey: String = "activityType"
        const val unitTypeKey: String = "unitType"
        const val measureType: String = "measureType"
        const val lastUpdatedKey: String = "lastUpdated"
    }
}