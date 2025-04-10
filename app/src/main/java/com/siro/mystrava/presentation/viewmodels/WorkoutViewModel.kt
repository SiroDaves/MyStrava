package com.siro.mystrava.presentation.viewmodels

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import com.siro.mystrava.data.models.activites.ActivityItem
import com.siro.mystrava.domain.repositories.SessionRepository
import com.siro.mystrava.data.models.detail.ActivityDetail
import com.siro.mystrava.domain.repositories.HomeRepository
import com.siro.mystrava.domain.entities.*
import com.siro.mystrava.domain.repositories.SettingsRepo
import com.siro.mystrava.domain.repositories.WorkoutRepository
import com.siro.mystrava.presentation.home.widgets.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class WorkoutViewModel @Inject constructor(
    private val workoutRepo: WorkoutRepository,
) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _activity: MutableStateFlow<ActivityDetail?> = MutableStateFlow(null)
    val activity: StateFlow<ActivityDetail?> = _activity.asStateFlow()


    fun fetchActivityDetails(activityId: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val activity = workoutRepo.loadActivityDetail(activityId)
                _activity.value = activity
            } catch (e: IOException) {
                _uiState.tryEmit(UiState.Error("Failed to load activity: Network error"))
            } catch (e: HttpException) {
                _uiState.tryEmit(UiState.Error("Failed to load activity: Server error"))
            } catch (e: Exception) {
                _uiState.tryEmit(UiState.Error("Failed to load activity: Unexpected error"))
            }

        }
    }
}