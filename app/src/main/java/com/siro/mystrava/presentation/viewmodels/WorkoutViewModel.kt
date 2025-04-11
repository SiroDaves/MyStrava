package com.siro.mystrava.presentation.viewmodels

import androidx.lifecycle.*
import com.siro.mystrava.data.models.detail.ActivityDetail
import com.siro.mystrava.domain.entities.*
import com.siro.mystrava.domain.repositories.WorkoutRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class WorkoutViewModel @Inject constructor(
    private val workoutRepo: WorkoutRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _activity: MutableStateFlow<ActivityDetail?> = MutableStateFlow(null)
    val activity: StateFlow<ActivityDetail?> = _activity.asStateFlow()

    // Get the activityId from savedStateHandle
    private val activityId: String = savedStateHandle.get<String>("activityId") ?: ""

    init {
        // Auto-fetch data when ViewModel is created
        if (activityId.isNotEmpty()) {
            fetchActivityDetails(activityId)
        }
    }

    fun fetchActivityDetails(activityId: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val activity = workoutRepo.loadActivityDetail(activityId)
                _activity.value = activity
            } catch (e: IOException) {
                _uiState.value = UiState.Error("Failed to load activity: Network error")
            } catch (e: HttpException) {
                _uiState.value = UiState.Error("Failed to load activity: Server error")
            } catch (e: Exception) {
                _uiState.value = UiState.Error("Failed to load activity: Unexpected error")
            }
        }
    }
}