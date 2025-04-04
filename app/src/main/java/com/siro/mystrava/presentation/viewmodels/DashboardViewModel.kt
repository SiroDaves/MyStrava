package com.siro.mystrava.presentation.viewmodels

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import com.siro.mystrava.data.models.detail.ActivityDetail
import com.siro.mystrava.data.repositories.*
import com.siro.mystrava.domain.entities.*
import com.siro.mystrava.domain.repositories.*
import com.siro.mystrava.presentation.screens.dashboard.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val dashRepo: DashboardRepository,
    private val stravaSessionRepository: SessionRepository,
    private val settingsRepo: SettingsRepository,
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

    var _weeklyActivityDetails: MutableLiveData<List<ActivityDetail>> = MutableLiveData()
    var weeklyActivityDetails: LiveData<List<ActivityDetail>> = _weeklyActivityDetails

    val _yearlySummaryMetrics: MutableLiveData<List<SummaryMetrics>> = MutableLiveData()
    val yearlySummaryMetrics: MutableLiveData<List<SummaryMetrics>> = _yearlySummaryMetrics

    init {
        _isLoggedInStrava.postValue(stravaSessionRepository.isLoggedIn())
    }

    fun fetchData() {
        _activityUiState.tryEmit(ActivityUiState.Loading)

        _activityType.postValue(dashRepo.getPreferredActivity())

        _unitType.postValue(dashRepo.getPreferredUnitType())

        viewModelScope.launch {
            dashRepo.loadActivities(
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

                dashRepo.widgetStatus.collect {
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
            _isLoggedInStrava.postValue(stravaSessionRepository.isLoggedIn())
        }
    }

    fun logout() {
        stravaSessionRepository.logOff()
        _isLoggedInStrava.postValue(false)
    }

    fun updateSelectedActivity(activityType: ActivityType) {
        dashRepo.savePreferredActivity(activityType)
        _activityType.postValue(dashRepo.getPreferredActivity())
    }

    fun updateSelectedUnit(unitType: UnitType) {
        dashRepo.savePreferredUnits(unitType = unitType)
        _unitType.postValue(dashRepo.getPreferredUnitType())
    }

    fun updateMeasureType(measureType: MeasureType) {
        dashRepo.saveMeasureType(measureType = measureType)
        _measureType.postValue(dashRepo.getPreferredMeasureType())

    }

    fun saveWeeklyStats(weeklyDistance: String, weeklyElevation: String) {
        dashRepo.saveWeeklyDistance(weeklyDistance, weeklyElevation)
    }

    fun loadWeekActivityDetails(weeklyActivityIds : List<String>) {
        Log.d("TAG", "loadWeekActivityDetails: $weeklyActivityIds")
        viewModelScope.launch {
            dashRepo.loadActivityDetails(weeklyActivityIds)
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