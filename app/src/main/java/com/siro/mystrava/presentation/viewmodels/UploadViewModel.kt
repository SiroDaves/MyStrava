package com.siro.mystrava.presentation.viewmodels

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.*
import com.siro.mystrava.data.models.activites.*
import com.siro.mystrava.data.models.detail.ActivityDetail
import com.siro.mystrava.domain.repositories.WorkoutRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.File
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class UploadViewModel @Inject constructor(
    private val workoutRepo: WorkoutRepository,
) : ViewModel() {

    private val _uiState: MutableStateFlow<UploadUiState> =
        MutableStateFlow(UploadUiState.Loaded)
    val uiState: StateFlow<UploadUiState> = _uiState.asStateFlow()

    private val _activity: MutableStateFlow<ActivityDetail?> = MutableStateFlow(null)
    val activity: StateFlow<ActivityDetail?> = _activity.asStateFlow()

    private val _selectedFile: MutableStateFlow<File?> = MutableStateFlow(null)
    val selectedFile: StateFlow<File?> = _selectedFile.asStateFlow()

    fun selectFile(context: Context, uri: Uri) {
        viewModelScope.launch {
            _uiState.value = UploadUiState.Loading
            try {
                val inputStream = context.contentResolver.openInputStream(uri)
                val file = File(context.cacheDir, "upload_${System.currentTimeMillis()}.fit")
                inputStream?.use { stream ->
                    file.outputStream().use { output ->
                        stream.copyTo(output)
                    }
                }
                _selectedFile.value = file
                _uiState.value = UploadUiState.FileSelected
            } catch (e: Exception) {
                _uiState.value = UploadUiState.Error("Error reading file: ${e.message}")
            }
        }
    }

    fun uploadSelectedFile() {
        viewModelScope.launch {
            _uiState.value = UploadUiState.Uploading
            try {
                /*val modifiedFile = fitFileProcessor.modifyFitFile(selectedFile!!)

                stravaUploader.uploadFile(
                    file = modifiedFile,
                    accessToken = "your_access_token", // Get from secure storage
                    onProgress = { progress ->
                        uploadProgress = progress
                        uploadStatus = UploadStatus.Uploading(progress)
                    },
                    onComplete = { uploadStatus = UploadStatus.Success },
                    onError = { error ->
                        errorMessage = error
                        uploadStatus = UploadStatus.Error
                    }
                )*/
                _uiState.value = UploadUiState.Success
            } catch (e: Exception) {
                _uiState.value = UploadUiState.Error("Upload failed: ${e.message}")
            }
        }
    }

}

sealed class UploadUiState {
    object Loaded : UploadUiState()
    object Loading : UploadUiState()
    object Uploading : UploadUiState()
    object FileSelected : UploadUiState()
    object Success : UploadUiState()
    class Error(val errorMessage: String) : UploadUiState()
}
