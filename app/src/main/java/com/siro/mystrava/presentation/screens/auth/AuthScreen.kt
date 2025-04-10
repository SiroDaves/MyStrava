package com.siro.mystrava.presentation.screens.auth

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.siro.mystrava.R
import com.siro.mystrava.presentation.viewmodels.HomeViewModel
import com.siro.mystrava.presentation.theme.primaryColorShade1

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthScreen(
    showLoginDialog: Boolean,
    onLoginClick: () -> Unit,
    onDialogDismiss: () -> Unit,
    viewModel: HomeViewModel
) {
    Scaffold(
        containerColor = primaryColorShade1,
        contentColor = MaterialTheme.colorScheme.onSurface
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Make it your ...",
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(8.dp))

                Image(
                    painter = painterResource(id = R.drawable.strava),
                    contentDescription = "Strava",
                    modifier = Modifier
                        .size(width = 300.dp, height = 100.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .shadow(8.dp)
                )

                Spacer(modifier = Modifier.height(40.dp))

                Text(
                    text = "Track your rides & progress effortlessly.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )

                Spacer(modifier = Modifier.height(10.dp))

                Image(
                    painter = painterResource(id = R.drawable.connect_with_strava),
                    contentDescription = null,
                    modifier = Modifier.size(width = 772f.dp, height = 192f.dp)
                        .clickable { onLoginClick() }
                )
            }
        }

        if (showLoginDialog) {
            Dialog(onDismissRequest = { onDialogDismiss() }) {
                StravaAuthWebView(viewModel = viewModel, onFinish = onDialogDismiss)
            }
        }
    }
}
