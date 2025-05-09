package com.siro.mystrava.domain.repositories

import android.content.*
import android.util.Log
import androidx.compose.ui.text.font.FontSynthesis.Companion.All
import com.siro.mystrava.R
import com.siro.mystrava.data.sources.remote.ActivitiesApi
import com.siro.mystrava.data.sources.local.ActivitiesDao
import com.siro.mystrava.domain.entities.*
import com.siro.mystrava.core.utils.*
import com.siro.mystrava.data.models.activites.ActivityItem
import com.siro.mystrava.data.sources.local.AppDatabase
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

    fun getAthleteActivities(
        before: Int?,
        after: Int?
    ): Flow<List<ActivityItem>> = flow {
        val activities: MutableList<ActivityItem> = mutableListOf()
        activities.addAll(
            activitiesApi.getAthleteActivities(
                before = before,
                after = after,
            )
        )
        emit(activities)
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