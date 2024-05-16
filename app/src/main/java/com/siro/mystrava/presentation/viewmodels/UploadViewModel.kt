package com.siro.mystrava.presentation.viewmodels

import android.content.Context
import android.net.Uri
import androidx.lifecycle.*
import com.siro.mystrava.data.models.detail.ActivityDetail
import com.siro.mystrava.domain.repositories.WorkoutRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
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

    private val _selectedFile: MutableStateFlow<Uri?> = MutableStateFlow(null)
    val selectedFile: StateFlow<Uri?> = _selectedFile.asStateFlow()

    fun selectFile(fileUri: Uri) {
        _selectedFile.value = fileUri
        _uiState.value = UploadUiState.FileSelected
    }

    private fun createMultipartBody(uri: Uri, context: Context): MultipartBody.Part {
        val file = context.contentResolver.openInputStream(uri)?.use { inputStream ->
            val file = File(context.cacheDir, "temp_file")
            file.outputStream().use { outputStream ->
                inputStream.copyTo(outputStream)
            }
            file
        } ?: throw IOException("Could not open file")

        return MultipartBody.Part.createFormData(
            "file",
            file.name,
            file.asRequestBody(context.contentResolver.getType(uri)?.toMediaType())
        )
    }

    fun uploadSelectedFile(context: Context) {
        val uri = _selectedFile.value ?: run {
            _uiState.value = UploadUiState.Error("No file selected")
            return
        }
        viewModelScope.launch {
            _uiState.value = UploadUiState.Uploading
            try {
                val filePart = createMultipartBody(uri, context)
                val result = workoutRepo.uploadActivity(filePart, "", "", "fit")
                if (result.error.isEmpty()) {
                    _uiState.value = UploadUiState.Success
                } else {
                    _uiState.value = UploadUiState.Error("Upload failed: ${result.error}")
                }
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
