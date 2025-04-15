package com.siro.mystrava.presentation.screens.workout.widgets

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.siro.mystrava.core.utils.ApiConstants

@Composable
fun MetricItem(value: String, label: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = value,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun DetailRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun DoneExportingDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit
) {
    AlertDialog(
        icon = {
            Icon(Icons.Filled.Check, contentDescription = "Exported")
        },
        title = {
            Text("Workout has been exported")
        },
        text = {
            Text("Your workout has been exported successfully.\n\nPlease choose how you want to proceed.")
        },
        onDismissRequest = onDismissRequest,
        confirmButton = {
            TextButton(onClick = onConfirmation) {
                Text("Edit & Re-upload")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text("Dismiss")
            }
        }
    )
}

@Composable
fun ExportActivity(activity: String) {
    val context = LocalContext.current
    FloatingActionButton(
        onClick = {
            //viewModel.exportOriginal(activity.id.toString())
            val intentUri = Uri.parse(ApiConstants.Uri.ACTIVITIES)
                .buildUpon().appendPath("$activity/export_original")
                .build()

            val intent = Intent(Intent.ACTION_VIEW, intentUri)
            context.startActivity(intent)
        },
    ) {
        Icon(Icons.Filled.KeyboardArrowDown, "Export")
    }
}

@Composable
fun CopyableLinkCard(activity: String) {
    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current
    val toastMessage = "Link copied!"
    val link = "${ApiConstants.Uri.ACTIVITIES}$activity/export_original"

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = link,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(12.dp))
            FloatingActionButton(
                onClick = {
                    clipboardManager.setText(AnnotatedString(link))
                    Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show()
                }
            ) {
                Icon(Icons.Filled.KeyboardArrowDown, "Export")
            }
        }
    }
}
