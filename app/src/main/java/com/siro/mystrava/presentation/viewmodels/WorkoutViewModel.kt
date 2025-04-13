package com.siro.mystrava.presentation.viewmodels

import androidx.lifecycle.*
import com.siro.mystrava.data.models.activites.*
import com.siro.mystrava.data.models.detail.ActivityDetail
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
) : ViewModel() {

    private val _uiState: MutableStateFlow<WorkoutUiState> =
        MutableStateFlow(WorkoutUiState.Loading)
    val uiState: StateFlow<WorkoutUiState> = _uiState.asStateFlow()

    private val _activity: MutableStateFlow<ActivityDetail?> = MutableStateFlow(null)
    val activity: StateFlow<ActivityDetail?> = _activity.asStateFlow()

    private val _stream: MutableStateFlow<Stream?> = MutableStateFlow(null)
    val stream: StateFlow<Stream?> = _stream.asStateFlow()

    private val _isExported: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isExported: StateFlow<Boolean> = _isExported.asStateFlow()

    fun setEditing() {
        _uiState.value = WorkoutUiState.Editing
    }

    fun setLoaded() {
        _uiState.value = WorkoutUiState.Loaded
    }

    fun fetchActivityDetails(activityId: String) {
        viewModelScope.launch {
            _uiState.value = WorkoutUiState.Loading
            try {
                val activity = workoutRepo.loadActivityDetail(activityId)
                _activity.value = activity
                _uiState.value = WorkoutUiState.Loaded
            } catch (e: IOException) {
                _uiState.value = WorkoutUiState.Error("Failed to load activity: Network error")
            } catch (e: HttpException) {
                _uiState.value = WorkoutUiState.Error("Failed to load activity: Server error")
            } catch (e: Exception) {
                _uiState.value = WorkoutUiState.Error("Failed to load activity: Unexpected error")
            }
        }
    }

    fun fetchActivityStream(activityId: String) {
        viewModelScope.launch {
            _uiState.value = WorkoutUiState.Exporting
            try {
                val stream = workoutRepo.loadActivityStream(activityId)
                _stream.value = stream
                _uiState.value = WorkoutUiState.Exported
            } catch (e: IOException) {
                _uiState.value = WorkoutUiState.Error(
                    "Failed to load stream: Network error\n\n$e"
                )
            } catch (e: HttpException) {
                _uiState.value = WorkoutUiState.Error(
                    "Failed to load stream: Server error\n\n$e"
                )
            } catch (e: Exception) {
                _uiState.value =
                    WorkoutUiState.Error(
                        "Failed to load stream: Unexpected error\n\n$e"
                    )
            }
        }
    }


    fun updateActivity(activityItem: ActivityItem, updateActivity: ActivityUpdate) {
        viewModelScope.launch {
            _uiState.value = WorkoutUiState.Loading
            //workoutRepo.updateActivity(activityItem, updateActivity)
            _uiState.value = WorkoutUiState.Loaded
        }
    }

}

sealed class WorkoutUiState {
    object Loading : WorkoutUiState()
    object Saving : WorkoutUiState()
    object Editing : WorkoutUiState()
    object Exporting : WorkoutUiState()
    object Exported : WorkoutUiState()
    object Loaded : WorkoutUiState()
    object Success : WorkoutUiState()
    class Error(val errorMessage: String) : WorkoutUiState()
}
