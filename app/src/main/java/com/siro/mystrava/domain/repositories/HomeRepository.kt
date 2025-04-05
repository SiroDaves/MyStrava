package com.siro.mystrava.domain.repositories

import android.content.*
import android.util.Log
import com.siro.mystrava.R
import com.siro.mystrava.data.models.activites.ActivityItem
import com.siro.mystrava.data.sources.remote.ActivitiesApi
import com.siro.mystrava.data.sources.local.ActivitiesDao
import com.siro.mystrava.data.models.detail.ActivityDetail
import com.siro.mystrava.data.sources.local.AppDatabase
import com.siro.mystrava.presentation.viewmodels.*
import com.siro.mystrava.presentation.viewmodels.ActivityType.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.*
import java.time.*
import javax.inject.*

@Singleton
class HomeRepository @Inject constructor(
    val context: Context,
    private val activitiesApi: ActivitiesApi
) : SharedPreferences.OnSharedPreferenceChangeListener {
    private val preferences: SharedPreferences = context.getSharedPreferences(
        context.getString(R.string.preference_file_key),
        Context.MODE_PRIVATE
    )

    private var activitiesDao: ActivitiesDao?

    private val _widgetStatus = MutableSharedFlow<Boolean>(replay = 0)

    fun refreshPrefs(): Boolean {
        return preferences.getBoolean("widgetEnable", false)
    }

    init {
        val db = AppDatabase.getDatabase(context)
        activitiesDao = db?.activitiesDao()

        preferences.registerOnSharedPreferenceChangeListener(this)
        _widgetStatus.tryEmit(refreshPrefs())
    }

    suspend fun getRecentActivities(): List<ActivityItem> = withContext(Dispatchers.IO) {
        val oneWeekAgoEpoch = LocalDate.now()
            .minusWeeks(1)
            .atStartOfDay(ZoneId.systemDefault())
            .toEpochSecond()
            .toInt()

        activitiesApi.getRecentActivities(after = oneWeekAgoEpoch)
    }

    suspend fun loadActivityDetails(activities: List<String>): Flow<List<Pair<String, ActivityDetail>>> =
        flow {
            val activityMap: MutableList<Pair<String, ActivityDetail>> = mutableListOf()

            activities.forEach { activityId ->
                withContext(Dispatchers.IO) {
                    val detail = activitiesApi.getActivityDetail(activityId)
                    activityMap.add(activityId to detail)
                }
            }

            emit(activityMap)
        }

    suspend fun saveActiivty(activitiesItem: ActivityItem) {
        withContext(Dispatchers.IO) {
            activitiesDao?.insertAll(activitiesItem)
        }
    }

    fun savePreferredActivity(activityType: ActivityType) {
        preferences.edit().putString(activityTypeKey, activityType.name).apply()
    }

    fun getPreferredActivity() =
        preferences.getString(activityTypeKey, Run.name)?.let {
            valueOf(
                it
            )
        } ?: All

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
    }
}