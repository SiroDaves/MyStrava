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
class UploadViewModel @Inject constructor(
    private val workoutRepo: WorkoutRepository,
) : ViewModel() {

    private val _uiState: MutableStateFlow<UploadUiState> =
        MutableStateFlow(UploadUiState.Loading)
    val uiState: StateFlow<UploadUiState> = _uiState.asStateFlow()

    private val _activity: MutableStateFlow<ActivityDetail?> = MutableStateFlow(null)
    val activity: StateFlow<ActivityDetail?> = _activity.asStateFlow()

    fun setLoaded() {
        _uiState.value = UploadUiState.Loaded
    }

    fun fetchActivityDetails(activityId: String) {
        viewModelScope.launch {
            _uiState.value = UploadUiState.Loading
            try {
                val activity = workoutRepo.loadActivityDetail(activityId)
                _activity.value = activity
                _uiState.value = UploadUiState.Loaded
            } catch (e: IOException) {
                _uiState.value = UploadUiState.Error("Failed to load activity: Network error")
            } catch (e: HttpException) {
                _uiState.value = UploadUiState.Error("Failed to load activity: Server error")
            } catch (e: Exception) {
                _uiState.value = UploadUiState.Error("Failed to load activity: Unexpected error")
            }
        }
    }

    fun updateActivityItem(activityItem: ActivityItem, updateActivity: ActivityUpdate) {
        viewModelScope.launch {
            _uiState.value = UploadUiState.Loading
            try {
                _uiState.value = UploadUiState.Loading
                workoutRepo.updateActivityItem(activityItem, updateActivity)
                _uiState.value = UploadUiState.Loaded
            } catch (e: IOException) {
                _uiState.value =
                    UploadUiState.Error("Failed to update activity: Network error\n\n$e")
            } catch (e: HttpException) {
                _uiState.value =
                    UploadUiState.Error("Failed to update activity: Server error\n\n$e")
            } catch (e: Exception) {
                _uiState.value =
                    UploadUiState.Error("Failed to update activity: Unexpected error\n\n$e")
            }
            _uiState.value = UploadUiState.Loaded
        }
    }

}

sealed class UploadUiState {
    object Loading : UploadUiState()
    object Uploading : UploadUiState()
    object Loaded : UploadUiState()
    object Success : UploadUiState()
    class Error(val errorMessage: String) : UploadUiState()
}
