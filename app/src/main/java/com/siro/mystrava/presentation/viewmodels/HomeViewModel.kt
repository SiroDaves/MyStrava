package com.siro.mystrava.presentation.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import com.siro.mystrava.data.models.activites.ActivityItem
import com.siro.mystrava.data.repositories.*
import com.siro.mystrava.domain.repositories.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepo: HomeRepository,
    private val sessionRepository: SessionRepository,
    private val settingsRepo: SettingsRepository,
) : ViewModel() {

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> get() = _loading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> get() = _error

    private var _isLoggedInStrava: MutableLiveData<Boolean> = MutableLiveData(null)
    var isLoggedInStrava: LiveData<Boolean> = _isLoggedInStrava

    private val _activities = MutableStateFlow<List<ActivityItem>>(emptyList())
    val activities: StateFlow<List<ActivityItem>> get() = _activities

    var _activityType: MutableLiveData<ActivityType> = MutableLiveData()
    var activityType: LiveData<ActivityType> = _activityType

    init {
        _isLoggedInStrava.postValue(sessionRepository.isLoggedIn())
    }

    fun fetchData() {
        _activityType.postValue(homeRepo.getPreferredActivity())
        viewModelScope.launch {
            try {
                val activities = withContext(Dispatchers.IO) {
                    homeRepo.getRecentActivities()
                }
                _activities.emit(activities)
            } catch (e: Exception) { }
        }
    }

    fun loginAthlete(code: String) {
        viewModelScope.launch {
            settingsRepo.authAthlete(code)
            _isLoggedInStrava.postValue(sessionRepository.isLoggedIn())
        }
    }

    fun logout() {
        sessionRepository.logOff()
        _isLoggedInStrava.postValue(false)
    }

    fun updateSelectedActivity(activityType: ActivityType) {
        homeRepo.savePreferredActivity(activityType)
        _activityType.postValue(homeRepo.getPreferredActivity())
    }
}

enum class ActivityType { Run, Swim, Bike, All }
enum class UnitType { Imperial, Metric }
enum class MeasureType { Absolute, Relative }
