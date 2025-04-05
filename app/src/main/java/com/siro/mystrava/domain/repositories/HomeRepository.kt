package com.siro.mystrava.domain.repositories

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.siro.mystrava.R
import com.siro.mystrava.core.utils.*
import com.siro.mystrava.data.models.activites.ActivityItem
import com.siro.mystrava.data.sources.remote.ActivitiesApi
import com.siro.mystrava.data.sources.local.ActivitiesDao
import com.siro.mystrava.strava.model.activites.db.ActivitiesDatabase
import com.siro.mystrava.data.models.detail.ActivityDetail
import com.siro.mystrava.domain.entities.*
import com.siro.mystrava.presentation.viewmodels.*
import com.siro.mystrava.presentation.viewmodels.ActivityType.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.*
import java.time.*
import javax.inject.*
import kotlin.math.abs

@Singleton
class HomeRepository @Inject constructor(
    val context: Context,
    private val activitiesApi: ActivitiesApi
) : SharedPreferences.OnSharedPreferenceChangeListener {

    //Cache in memory the strava workouts
    private lateinit var listOfStravaWorkouts: List<ActivityItem>

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
        val db = ActivitiesDatabase.getDatabase(context)
        activitiesDao = db?.activitiesDao()

        preferences.registerOnSharedPreferenceChangeListener(this)
        _widgetStatus.tryEmit(refreshPrefs())
    }

    suspend fun getRecentActivities(): List<ActivityItem> {
        var activities: List<ActivityItem>

        withContext(Dispatchers.IO) {
            activities = activitiesDao?.getLast10Activities() ?: emptyList()
        }

        return activities
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

    fun getStravaActivitiesBeforeAndAfterPaginated(
        before: Int?,
        after: Int?
    ): Flow<List<ActivityItem>> = flow {
        val yearActivities: MutableList<ActivityItem> = mutableListOf()
        var pageCount = 1
        do {
            yearActivities.addAll(
                activitiesApi.getAthleteActivitiesBeforeAndAfter(
                    before = before,
                    after = after,
                    page = pageCount
                )
            )
            pageCount = pageCount.inc()

            //Add activities to db
            yearActivities.map {
                runBlocking {
                    saveActivty(it)
                }
            }

        } while (yearActivities.size % 200 == 0 && yearActivities.size != 0)

        emit(yearActivities)
    }

    //Write activity to db
    suspend fun saveActivty(activitiesItem: ActivityItem) {
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

    fun savePreferredUnits(unitType: UnitType) {
        preferences.edit().putString(unitTypeKey, unitType.name).apply()
    }

    fun saveMeasureType(measureType: MeasureType) {
        preferences.edit().putString(Companion.measureType, measureType.name).apply()
    }

    fun getPreferredMeasureType() =
        preferences.getString(measureType, MeasureType.Absolute.name)?.let {
            MeasureType.valueOf(
                it
            )
        } ?: MeasureType.Absolute

    fun getPreferredUnitType() =
        preferences.getString(unitTypeKey, UnitType.Imperial.name)?.let {
            UnitType.valueOf(
                it
            )
        } ?: UnitType.Imperial

    fun saveLastFetchTimestamp() {
        val currentTime = LocalDateTime.now()
        preferences.edit().putString(lastUpdatedKey, currentTime.toString()).apply()
    }

    fun fetchLastUpdatedTime(): LocalDateTime? {
        val lastUpdatedString = preferences.getString(lastUpdatedKey, "")
        return if (lastUpdatedString.isNullOrEmpty())
            null
        else
            LocalDateTime.parse(lastUpdatedString)
    }

    fun saveWeeklyDistance(weeklyDistance: String, weeklyElevation: String) {
        preferences.edit().putString("weeklyDistance", weeklyDistance).apply()
        preferences.edit().putString("weeklyElevation", weeklyElevation).apply()
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