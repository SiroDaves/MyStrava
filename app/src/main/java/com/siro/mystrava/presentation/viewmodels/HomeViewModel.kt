package com.siro.mystrava.presentation.viewmodels

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.siro.mystrava.domain.repositories.SessionRepository
import com.siro.mystrava.data.models.activitydetail.StravaActivityDetail
import com.siro.mystrava.domain.repositories.HomeRepository
import com.siro.mystrava.presentation.dashboard.SummaryMetrics
import com.siro.mystrava.domain.entities.CalendarActivities
import com.siro.mystrava.domain.entities.CalendarData
import com.siro.mystrava.presentation.dashboard.getStats
import com.siro.mystrava.domain.repositories.SettingsRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepo: HomeRepository,
    private val sessionRepo: SessionRepository,
    private val settingsRepo: SettingsRepo,
) : ViewModel() {

    val widgetStatus = mutableStateOf(false)

    private var _isLoggedInStrava: MutableLiveData<Boolean> = MutableLiveData(null)
    var isLoggedInStrava: LiveData<Boolean> = _isLoggedInStrava

    private val _activityUiState: MutableStateFlow<ActivityUiState> =
        MutableStateFlow(ActivityUiState.Loading)
    val activityUiState: StateFlow<ActivityUiState> = _activityUiState.asStateFlow()

    var _activityType: MutableLiveData<ActivityType> = MutableLiveData()
    var activityType: LiveData<ActivityType> = _activityType

    var _unitType: MutableLiveData<UnitType> = MutableLiveData()
    var unitType: LiveData<UnitType> = _unitType

    var _measureType: MutableLiveData<MeasureType> = MutableLiveData()
    var measureType: LiveData<MeasureType> = _measureType

    val calendarData = CalendarData()

    var _weeklyActivityDetails: MutableLiveData<List<StravaActivityDetail>> = MutableLiveData()
    var weeklyActivityDetails: LiveData<List<StravaActivityDetail>> = _weeklyActivityDetails

    val _yearlySummaryMetrics: MutableLiveData<List<SummaryMetrics>> = MutableLiveData()
    val yearlySummaryMetrics: MutableLiveData<List<SummaryMetrics>> = _yearlySummaryMetrics

    init {
        _isLoggedInStrava.postValue(sessionRepo.isLoggedIn())
    }

    fun fetchData() {
        _activityUiState.tryEmit(ActivityUiState.Loading)

        _activityType.postValue(homeRepo.getPreferredActivity())

        _unitType.postValue(homeRepo.getPreferredUnitType())

        viewModelScope.launch {
            homeRepo.loadActivities(
                after = null,
                before = calendarData.currentYear.first,
            ).catch { exception ->
                Log.d("TAG", "fetchData: $exception")
                val errorCode = (exception as HttpException).code()

                val errorMessage = if (errorCode in 400..499) {
                    "Error! Force Refresh"
                } else {
                    "Have issues connecting to Strava"
                }

                _activityUiState.tryEmit(ActivityUiState.Error(errorMessage))
            }.collect { currentYearActivities ->
                _activityUiState.tryEmit(ActivityUiState.DataLoaded(currentYearActivities))

                yearlySummaryMetrics(currentYearActivities)

                homeRepo.widgetStatus.collect {
                    widgetStatus.value = it
                }

            }
        }
    }

    private fun yearlySummaryMetrics(currentYearActivities: CalendarActivities) {
        val preferredActivity = currentYearActivities.preferredActivityType
        _yearlySummaryMetrics.postValue( buildList {
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

    fun loadWeekActivityDetails(weeklyActivityIds : List<String>) {
        Log.d("TAG", "loadWeekActivityDetails: $weeklyActivityIds")
        viewModelScope.launch {
            homeRepo.loadActivityDetails(weeklyActivityIds)
                .collectLatest {
                    _weeklyActivityDetails.postValue(it.map { it.second })
                }
        }
    }
}

enum class ActivityType { Run, Swim, Bike, All }
enum class UnitType { Imperial, Metric }
enum class MeasureType { Absolute, Relative }

sealed class ActivityUiState {
    object Loading : ActivityUiState()
    class DataLoaded(val calendarActivities: CalendarActivities) : ActivityUiState()
    class Error(val errorMessage: String) : ActivityUiState()
}