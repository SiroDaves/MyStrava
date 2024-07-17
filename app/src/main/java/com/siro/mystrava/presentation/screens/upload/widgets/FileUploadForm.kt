package com.siro.mystrava.presentation.screens.upload.widgets

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.dp
import com.siro.mystrava.core.utils.getFileName
import com.siro.mystrava.data.models.fit.*
import com.siro.mystrava.presentation.viewmodels.UploadViewModel
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FileUploadForm(
    context: Context,
    viewModel: UploadViewModel,
) {
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var dataType by remember { mutableStateOf("") }
    var deviceName by remember { mutableStateOf("") }
    val selectedFile by viewModel.selectedFile.collectAsState()

    val fitFile = remember(selectedFile) {
        selectedFile?.let { uri ->
            val name = context.contentResolver.getFileName(uri)
            val tempFile = File(context.cacheDir, name)
            context.contentResolver.openInputStream(uri)?.use { input ->
                tempFile.outputStream().use { output ->
                    input.copyTo(output)
                }
            }
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Title") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        TextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description") },
            modifier = Modifier
                .fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        TextField(
            value = dataType,
            onValueChange = { dataType = it },
            label = { Text("Data Type") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(16.dp))

        TextField(
            value = deviceName,
            onValueChange = { deviceName = it },
            label = { Text("Device Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = {
                viewModel.uploadSelectedFile(
                    context,
                    name,
                    description,
                    dataType,
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text("Upload Workout")
        }
    }
}