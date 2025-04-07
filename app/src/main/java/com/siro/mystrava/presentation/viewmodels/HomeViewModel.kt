package com.siro.mystrava.presentation.viewmodels

import androidx.lifecycle.*
import com.siro.mystrava.data.models.activites.ActivityItem
import com.siro.mystrava.domain.repositories.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepo: HomeRepository,
    private val sessionRepo: SessionRepository,
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

    // ✅ Add login in progress state
    private val _loginInProgress = MutableStateFlow(false)
    val loginInProgress: StateFlow<Boolean> get() = _loginInProgress

    init {
        _isLoggedInStrava.postValue(sessionRepo.isLoggedIn())
    }

    fun fetchData() {
        _activityType.postValue(homeRepo.getPreferredActivity())
        viewModelScope.launch {
            _loading.value = true
            _error.value = null
            try {
                Timber.d("Fetching activities...")
                val activities = withContext(Dispatchers.IO) {
                    homeRepo.getRecentActivities().also {
                        Timber.d("Fetched ${it.size} activities")
                    }
                }

                _activities.emit(activities)
            } catch (e: Exception) {
                _error.value = e.localizedMessage ?: "Failed to load activities"
            } finally {
                _loading.value = false
            }
        }
    }

    fun loginAthlete(code: String) {
        viewModelScope.launch {
            _loginInProgress.value = true
            try {
                settingsRepo.authAthlete(code)
                Timber.d("Login successful")
                _isLoggedInStrava.postValue(sessionRepo.isLoggedIn())
            } catch (e: Exception) {
                Timber.e(e, "Login failed")
                _isLoggedInStrava.postValue(false)
                _error.value = e.localizedMessage ?: "Login failed"
            } finally {
                _loginInProgress.value = false
            }
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
}

enum class ActivityType { Run, Swim, Bike, All }
enum class UnitType { Imperial, Metric }