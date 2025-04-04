package com.siro.mystrava.presentation.screens.auth

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.siro.mystrava.R
import com.siro.mystrava.presentation.theme.*
import com.siro.mystrava.presentation.viewmodels.DashboardViewModel

@Composable
fun AuthScreen(viewModel: DashboardViewModel) {
    var showLoginDialog by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = primaryColorShade1,
        contentColor = MaterialTheme.colorScheme.onSurface
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text("Welcome to My Strava!", style = MaterialTheme.typography.headlineMedium)
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .clickable { showLoginDialog = true },
                contentAlignment = Alignment.BottomCenter
            ) {
                Image(
                    painter = painterResource(id = R.drawable.connect_with_strava),
                    contentDescription = "Connect to Strava"
                )
            }
        }

        if (showLoginDialog) {
            Dialog(onDismissRequest = { showLoginDialog = false }) {
                WebAuthView(viewModel) { showLoginDialog = false }
            }
        }
    }
}
