package com.siro.mystrava.presentation.viewmodels

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import com.siro.mystrava.data.models.activites.ActivityItem
import com.siro.mystrava.domain.repositories.SessionRepository
import com.siro.mystrava.data.models.detail.ActivityDetail
import com.siro.mystrava.domain.repositories.HomeRepository
import com.siro.mystrava.domain.entities.*
import com.siro.mystrava.domain.repositories.SettingsRepository
import com.siro.mystrava.presentation.screens.home.widgets.SummaryMetrics
import com.siro.mystrava.presentation.screens.home.widgets.getStats
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.*
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepo: HomeRepository,
    private val sessionRepo: SessionRepository,
    private val settingsRepo: SettingsRepository,
) : ViewModel() {
    val widgetStatus = mutableStateOf(false)

    private var _isLoggedInStrava: MutableLiveData<Boolean> = MutableLiveData(null)
    var isLoggedInStrava: LiveData<Boolean> = _isLoggedInStrava

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    var _activityType: MutableLiveData<ActivityType> = MutableLiveData()
    var activityType: LiveData<ActivityType> = _activityType

    var _unitType: MutableLiveData<UnitType> = MutableLiveData()
    var unitType: LiveData<UnitType> = _unitType

    var _measureType: MutableLiveData<MeasureType> = MutableLiveData()
    var measureType: LiveData<MeasureType> = _measureType

    val calendarData = CalendarData()

    var _weeklyActivityDetails: MutableLiveData<List<ActivityDetail>> = MutableLiveData()
    var weeklyActivityDetails: LiveData<List<ActivityDetail>> = _weeklyActivityDetails

    val _yearlySummaryMetrics: MutableLiveData<List<SummaryMetrics>> = MutableLiveData()
    val yearlySummaryMetrics: MutableLiveData<List<SummaryMetrics>> = _yearlySummaryMetrics

    private val _activities = MutableStateFlow<List<ActivityItem>>(emptyList())
    val activities: StateFlow<List<ActivityItem>> get() = _activities

    init {
        _isLoggedInStrava.postValue(sessionRepo.isLoggedIn())
    }

    fun fetchData() {
        _uiState.tryEmit(UiState.Loading)
        _unitType.postValue(homeRepo.getPreferredUnitType())

        viewModelScope.launch {
            launch {
                homeRepo.widgetStatus.collect {
                    widgetStatus.value = it
                }
            }
            homeRepo.loadActivities(
                after = null,
                before = calendarData.currentYear.first,
            ).catch { exception ->
                Log.d("TAG", "fetchData: $exception")
                val errorCode = (exception as? HttpException)?.code()

                val errorMessage = if (errorCode in 400..499) {
                    "Error! Force Refresh"
                } else {
                    "We have some issues connecting to Strava: $exception"
                }
                _uiState.tryEmit(UiState.Error(errorMessage))
            }.collect { currentYearActivities ->
                _uiState.tryEmit(UiState.Loaded(currentYearActivities))
                yearlySummaryMetrics(currentYearActivities)
            }

            val activities = withContext(Dispatchers.IO) {
                homeRepo.getRecentActivities()
            }
            _activities.emit(activities)
        }
    }

    private fun yearlySummaryMetrics(currentYearActivities: CalendarActivities) {
        val preferredActivity = currentYearActivities.preferredActivityType
        _yearlySummaryMetrics.postValue(buildList {
            if (currentYearActivities.preferredMeasureType == MeasureType.Absolute) {
                add(currentYearActivities.currentYearActivities.getStats(preferredActivity))
                add(currentYearActivities.previousYearActivities.getStats(preferredActivity))
                add(currentYearActivities.twoYearsAgoActivities.getStats(preferredActivity))
            } else {
                add(currentYearActivities.relativeYearActivities.getStats(preferredActivity))
                add(currentYearActivities.relativePreviousYearActivities.getStats(preferredActivity))
                add(currentYearActivities.relativeTwoYearsAgoActivities.getStats(preferredActivity))
            }
        })
    }


    fun loginAthlete(code: String) {
        viewModelScope.launch {
            settingsRepo.authAthlete(code)
            _isLoggedInStrava.postValue(sessionRepo.isLoggedIn())
        }
    }

    fun logout() {
        sessionRepo.logOff()
        _isLoggedInStrava.postValue(false)
    }

    fun updateSelectedActivity(activityType: ActivityType) {
        homeRepo.savePreferredActivity(activityType)
        _activityType.postValue(homeRepo.getPreferredActivity())
    }

    fun updateSelectedUnit(unitType: UnitType) {
        homeRepo.savePreferredUnits(unitType = unitType)
        _unitType.postValue(homeRepo.getPreferredUnitType())
    }

    fun updateMeasureType(measureType: MeasureType) {
        homeRepo.saveMeasureType(measureType = measureType)
        _measureType.postValue(homeRepo.getPreferredMeasureType())

    }

    fun saveWeeklyStats(weeklyDistance: String, weeklyElevation: String) {
        homeRepo.saveWeeklyDistance(weeklyDistance, weeklyElevation)
    }

    fun loadWeekActivityDetails(weeklyActivityIds: List<String>) {
        Log.d("TAG", "loadWeekActivityDetails: $weeklyActivityIds")
        viewModelScope.launch {
            homeRepo.loadActivityDetails(weeklyActivityIds)
                .collectLatest {
                    _weeklyActivityDetails.postValue(it.map { it.second })
                }
        }
    }
}
