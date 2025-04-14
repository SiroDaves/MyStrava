package com.siro.mystrava.presentation.viewmodels

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import com.siro.mystrava.data.models.activites.ActivityItem
import com.siro.mystrava.domain.repositories.SessionRepository
import com.siro.mystrava.domain.entities.*
import com.siro.mystrava.domain.repositories.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.*
import retrofit2.HttpException
import java.time.Instant
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepo: HomeRepository,
    private val sessionRepo: SessionRepository,
    private val settingsRepo: SettingsRepository,
) : ViewModel() {
    private var _isLoggedInStrava: MutableLiveData<Boolean> = MutableLiveData(null)
    var isLoggedInStrava: LiveData<Boolean> = _isLoggedInStrava

    private val _uiState: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    val calendarData = CalendarData()

    private val _activities = MutableStateFlow<List<ActivityItem>>(emptyList())
    val activities: StateFlow<List<ActivityItem>> get() = _activities

    init {
        _isLoggedInStrava.postValue(sessionRepo.isLoggedIn())
    }

    fun fetchData() {
        _uiState.tryEmit(HomeUiState.Loading)

        viewModelScope.launch {
            homeRepo.getAthleteActivities(
                before = Instant.now().epochSecond.toInt(),
                after = calendarData.monthsAgo(1).toInt(),
            ).catch { exception ->
                Log.d("TAG", "fetchData: $exception")
                val errorCode = (exception as? HttpException)?.code()

                val errorMessage = if (errorCode in 400..499) {
                    "Error! Force Refresh"
                } else {
                    "We have some issues connecting to Strava: $exception"
                }
                _uiState.tryEmit(HomeUiState.Error(errorMessage))
            }.collect { latestActivities ->
                _activities.emit(latestActivities)
            }
            _uiState.tryEmit(HomeUiState.Loaded)
        }
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
}

enum class ActivityType { Run, Swim, Bike, All }
enum class UnitType { Imperial, Metric }
enum class MeasureType { Absolute, Relative }

sealed class HomeUiState {
    object Loading : HomeUiState()
    object Loaded : HomeUiState()
    class Error(val errorMessage: String) : HomeUiState()
}
