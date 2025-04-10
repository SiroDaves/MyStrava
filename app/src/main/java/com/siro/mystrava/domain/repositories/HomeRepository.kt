package com.siro.mystrava.domain.repositories

import android.content.*
import android.util.Log
import com.siro.mystrava.R
import com.siro.mystrava.data.sources.remote.ActivitiesApi
import com.siro.mystrava.data.sources.local.ActivitiesDao
import com.siro.mystrava.presentation.viewmodels.ActivityType.*
import com.siro.mystrava.domain.entities.*
import com.siro.mystrava.core.utils.*
import com.siro.mystrava.data.models.activites.ActivityItem
import com.siro.mystrava.data.models.detail.ActivityDetail
import com.siro.mystrava.data.sources.local.AppDatabase
import com.siro.mystrava.presentation.viewmodels.*
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
    val widgetStatus: SharedFlow<Boolean> = _widgetStatus

    val calendarData = CalendarData()

    fun refreshPrefs(): Boolean {
        return preferences.getBoolean("widgetEnable", false)
    }

    init {
        val db = AppDatabase.getDatabase(context)
        activitiesDao = db?.activitiesDao()

        preferences.registerOnSharedPreferenceChangeListener(this)
        _widgetStatus.tryEmit(refreshPrefs())
    }

    suspend fun getRecentActivities(): List<ActivityItem> {
        var allActivities: List<ActivityItem>
        withContext(Dispatchers.IO) {
            allActivities = activitiesDao?.getLast10Activities() ?: emptyList()
        }
        return allActivities
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

    fun loadActivities(
        before: Int? = null,
        after: Int? = null,
    ): Flow<CalendarActivities> = flow {
        var allActivities: List<ActivityItem>?

        //query db
        withContext(Dispatchers.IO) {
            allActivities = activitiesDao?.getAll()
        }

        var beforeDate = before
        var afterDate = after

        val lastUpdated = fetchLastUpdatedTime()
        var shouldCallApi = false

        if (lastUpdated != null) {
            val currentTime = LocalDateTime.now()
            if (currentTime > lastUpdated) {
                //Date outdated refreshing
                shouldCallApi = true
                val beforePair = getEpoch(
                    currentTime.year,
                    currentTime.monthValue - 1,
                    currentTime.dayOfMonth,
                    currentTime.hour,
                    currentTime.minute
                )
                beforeDate = beforePair.first

                Log.d("TAG", "loadActivities: ${beforePair.second}")

                if (allActivities != null) {
                    //get most recently stored activity to determine the "after date" to call the api
                    val date = allActivities!!.minByOrNull {
                        abs(
                            it.start_date.getDate().atStartOfDay()
                                .toEpochSecond(ZoneOffset.UTC) - currentTime.toEpochSecond(
                                ZoneOffset.UTC
                            )
                        )
                    }?.start_date?.getDateTime()

                    date?.let {
                        val afterDatePair = getEpoch(
                            it.year,
                            it.monthValue - 1,
                            it.dayOfMonth,
                            it.hour,
                            it.minute
                        )

                        Log.d("TAG", "loadActivities: 2nd ${beforePair.second}")

                        afterDate = afterDatePair.first
                    }
                }
            }
        }

        //Check db
        if (allActivities.isNullOrEmpty() || shouldCallApi) {
            val remoteActivities: Flow<List<ActivityItem>> = flow {
                val remote = getStravaActivitiesBeforeAndAfterPaginated(beforeDate, afterDate)

                remote.collect {
                    allActivities = it
                    emit(allActivities!!)
                }
            }

            remoteActivities.collect {
                shouldCallApi = false
                saveLastFetchTimestamp()
                withContext(Dispatchers.IO) {
                    allActivities = activitiesDao?.getAll()

                }
            }

        }

        allActivities?.let { activities ->

            var currentYearActivities: List<ActivityItem> =
                activities.filter { it.start_date.getDate().year == calendarData.currentYearInt }
            var previousYearActivities: List<ActivityItem> =
                activities.filter { it.start_date.getDate().year == calendarData.currentYearInt - 1 }
            var twoYearsAgoActivities: List<ActivityItem> =
                activities.filter { it.start_date.getDate().year == calendarData.currentYearInt - 2 }


            var relativeYearActivities: List<ActivityItem> =
                currentYearActivities.filter { it.start_date.getDate().dayOfYear < calendarData.currentDayOfYeah }
            var relativePreviousYearActivities: List<ActivityItem> =
                previousYearActivities.filter { it.start_date.getDate().dayOfYear < calendarData.currentDayOfYeah }
            var relativeTwoYearsAgoActivities: List<ActivityItem> =
                twoYearsAgoActivities.filter { it.start_date.getDate().dayOfYear < calendarData.currentDayOfYeah }


            val currentMonthActivities: List<ActivityItem> =
                currentYearActivities.filter { it.start_date.getDate().monthValue == calendarData.currentMonthInt }

            Log.d("TAG", "loadActivities: $currentMonthActivities")

            val previousMonthActivities: List<ActivityItem> =
                currentMonthActivities.plus(previousYearActivities).plus(currentYearActivities).filter {
                    if (calendarData.currentMonthInt == 1) {
                        it.start_date.getDate().monthValue == 12
                                && it.start_date.getDate().year == 2022
                    } else {
                        it.start_date.getDate().monthValue == calendarData.currentMonthInt - 1
                                && it.start_date.getDate().year == 2023
                    }
                }

            Log.d("TAG", "loadActivities: $previousMonthActivities")

            val twoMonthAgoActivities: List<ActivityItem> =
                currentMonthActivities.plus(previousYearActivities).plus(currentYearActivities).filter {
                    if (calendarData.currentMonthInt == 1) {
                        it.start_date.getDate().monthValue == 11
                                && it.start_date.getDate().year == 2022
                    } else {
                        it.start_date.getDate().monthValue == calendarData.currentMonthInt - 2
                                && it.start_date.getDate().year == 2023
                    }
                }

            Log.d("TAG", "loadActivities: $twoMonthAgoActivities")

            var relativeMonthActivities: List<ActivityItem> =
                currentMonthActivities.filter { it.start_date.getDate().dayOfMonth < calendarData.currentDateTime.dayOfMonth }
            var relativePreviousMonthActivities: List<ActivityItem> =
                previousMonthActivities.filter { it.start_date.getDate().dayOfMonth < calendarData.currentDateTime.dayOfMonth }
            var relativePrevPrevMonthActivities: List<ActivityItem> =
                twoMonthAgoActivities.filter { it.start_date.getDate().dayOfMonth < calendarData.currentDateTime.dayOfMonth }

            emit(
                CalendarActivities(
                    currentMonthActivities = currentMonthActivities,
                    previousMonthActivities = previousMonthActivities,
                    twoMonthAgoActivities = twoMonthAgoActivities,
                    currentYearActivities = currentYearActivities,
                    previousYearActivities = previousYearActivities,
                    twoYearsAgoActivities = twoYearsAgoActivities,
                    preferredActivityType = getPreferredActivity(),
                    selectedUnitType = getPreferredUnitType(),
                    preferredMeasureType = getPreferredMeasureType(),
                    relativeYearActivities = relativeYearActivities,
                    relativePreviousYearActivities = relativePreviousYearActivities,
                    relativeTwoYearsAgoActivities = relativeTwoYearsAgoActivities,
                    relativeMonthActivities = relativeMonthActivities,
                    relativePreviousMonthActivities = relativePreviousMonthActivities,
                    relativePrevPrevMonthActivities = relativePrevPrevMonthActivities
                )
            )
        }
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
    suspend fun saveActivty(ActivityItem: ActivityItem) {
        withContext(Dispatchers.IO) {
            activitiesDao?.insertAll(ActivityItem)
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