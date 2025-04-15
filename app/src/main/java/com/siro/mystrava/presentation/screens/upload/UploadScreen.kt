package com.siro.mystrava.presentation.screens.upload

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.siro.mystrava.presentation.viewmodels.UploadViewModel
import com.siro.mystrava.presentation.viewmodels.WorkoutUiState
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UploadScreen(
    viewModel: UploadViewModel,
    onBackPressed: () -> Unit,
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var selectedFile by remember { mutableStateOf<File?>(null) }
    var uploadProgress by remember { mutableStateOf(0f) }
    val uiState by viewModel.uiState.collectAsState()
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            /*uri?.let {
                scope.launch(Dispatchers.IO) {
                    try {
                        val inputStream = context.contentResolver.openInputStream(uri)
                        val file = File(context.cacheDir, "upload_${System.currentTimeMillis()}.fit")
                        inputStream?.use { stream ->
                            file.outputStream().use { output ->
                                stream.copyTo(output)
                            }
                        }
                        selectedFile = file
                        uploadStatus = UploadStatus.FileSelected
                    } catch (e: Exception) {
                        errorMessage = "Error reading file: ${e.message}"
                    }
                }
            }*/
        }
    )


    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier
                    .padding(10.dp)
                    .shadow(
                        elevation = 5.dp,
                        spotColor = Color.DarkGray,
                        shape = RoundedCornerShape(10.dp)
                    ),
                title = { Text("Upload a Workout") },
                navigationIcon = {
                    IconButton(onClick = onBackPressed) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Localized description"
                        )
                    }
                },
            )
        },
        content = {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // File selection
                Button(
                    onClick = { filePickerLauncher.launch("application/octet-stream") },
                    //enabled = uploadStatus !is UploadStatus.Uploading
                ) {
                    Text("Select FIT File")
                }

                // Selected file info
                selectedFile?.let {
                    Text(
                        text = "Selected file: ${it.name}",
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                // Upload button
                Button(
                    onClick = {
                        /*scope.launch(Dispatchers.IO) {
                            try {
                                uploadStatus = UploadStatus.Uploading(0f)
                                val modifiedFile = fitFileProcessor.modifyFitFile(selectedFile!!)

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
                                )
                            } catch (e: Exception) {
                                errorMessage = "Upload failed: ${e.message}"
                                uploadStatus = UploadStatus.Error
                            }
                        }*/
                    },
                    //enabled = selectedFile != null && uploadStatus !is UploadStatus.Uploading
                ) {
                    Text("Upload to Strava")
                }

                // Progress indicator
                /*when (val status = uploadStatus) {
                    is UploadStatus.Uploading -> {
                        LinearProgressIndicator(
                            progress = { status.progress },
                            modifier = Modifier.fillMaxWidth()
                        )
                        Text("Uploading... ${(status.progress * 100).toInt()}%")
                    }
                    is UploadStatus.Success -> {
                        Icon(Icons.Default.CheckCircle, contentDescription = "Success")
                        Text("Upload successful!", color = MaterialTheme.colorScheme.primary)
                    }
                    is UploadStatus.Error -> {
                        Icon(Icons.Default.Error, contentDescription = "Error")
                        Text("Upload failed", color = MaterialTheme.colorScheme.error)
                    }
                    else -> {}
                }*/

                // Error message
                errorMessage?.let {
                    Text(
                        text = it,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        },
    )
}