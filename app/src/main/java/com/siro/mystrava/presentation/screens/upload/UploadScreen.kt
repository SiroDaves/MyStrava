package com.siro.mystrava.presentation.screens.upload

import android.annotation.SuppressLint
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.siro.mystrava.presentation.screens.upload.widgets.FilePickerCard
import com.siro.mystrava.presentation.screens.workout.widgets.WorkoutContent
import com.siro.mystrava.presentation.screens.workout.widgets.WorkoutEditForm
import com.siro.mystrava.presentation.viewmodels.*
import com.siro.mystrava.presentation.widgets.*

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UploadScreen(
    viewModel: UploadViewModel,
    onBackPressed: () -> Unit,
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()
    val selectedFile by viewModel.selectedFile.collectAsState()

    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            uri?.let { viewModel.selectFile(context, uri) }
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
            Box(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
                    .background(color = MaterialTheme.colorScheme.surface)
            ) {
                Column(
                    modifier = Modifier.padding(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    FilePickerCard(
                        selectedFile = selectedFile,
                        onSelectClick = { filePickerLauncher.launch("application/octet-stream") },
                        onClearClick = { },
                        canSelect = uiState is UploadUiState.Uploading
                    )

                    when (uiState) {
                        is UploadUiState.Loaded -> {

                        }

                        is UploadUiState.FileSelected -> {
                            selectedFile?.let {
                                Text(
                                    text = "Selected file: ${it.name}",
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }

                            Button(
                                onClick = { viewModel.uploadSelectedFile() },
                            ) {
                                Text("Upload to Strava")
                            }
                        }

                        is UploadUiState.Loading -> LoadingState("Loading data ...")
                        is UploadUiState.Success -> {
                            Icon(Icons.Default.CheckCircle, contentDescription = "Success")
                            Text("Upload successful!", color = MaterialTheme.colorScheme.primary)
                        }

                        is UploadUiState.Uploading -> TODO()
                        is UploadUiState.Error -> {
                            val errorMessage = (uiState as UploadUiState.Error).errorMessage
                            ErrorState(
                                errorMessage = errorMessage,
                                onRetry = { }
                            )
                        }
                    }
                }
            }
        }
    )
}