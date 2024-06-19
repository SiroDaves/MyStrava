package com.siro.mystrava.presentation.screens.upload.widgets

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.dp
import com.siro.mystrava.core.utils.*

@SuppressLint("DefaultLocale")
@Composable
fun FilePickerCard(
    context: Context,
    selectedFile: Uri?,
    onSelectClick: () -> Unit,
    onClearClick: () -> Unit,
    canSelect: Boolean
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            val fileInfo = remember(selectedFile) {
                selectedFile?.let { uri ->
                    val name = context.contentResolver.getFileName(uri)
                    val size = context.contentResolver.getFormattedFileSize(uri)
                    "$name ($size)"
                } ?: "No file selected"
            }

            Text(
                text = "Selected file: $fileInfo",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = onSelectClick,
                    enabled = !canSelect
                ) {
                    Text("Select a File")
                }

                Button(
                    onClick = onClearClick,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer,
                        contentColor = MaterialTheme.colorScheme.onErrorContainer
                    ),
                    enabled = selectedFile != null
                ) {
                    Text("Clear")
                }
            }
        }
    }
}
