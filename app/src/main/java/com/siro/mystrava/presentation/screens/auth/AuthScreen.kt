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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.siro.mystrava.R
import com.siro.mystrava.presentation.theme.*
import com.siro.mystrava.presentation.viewmodels.HomeViewModel

@Composable
fun AuthScreen(viewModel: HomeViewModel) {
    var showLoginDialog by remember { mutableStateOf(false) }

    AuthScreenContent(
        showLoginDialog = showLoginDialog,
        onConnectClick = { showLoginDialog = true },
        onDismissDialog = { showLoginDialog = false },
        viewModel = viewModel
    )
}

@Composable
fun AuthScreenContent(
    showLoginDialog: Boolean,
    onConnectClick: () -> Unit,
    onDismissDialog: () -> Unit,
    viewModel: HomeViewModel? = null
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

                Button(
                    onClick = onConnectClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Text(text = "Connect with Strava")
                }
            }

            if (showLoginDialog && viewModel != null) {
                Dialog(onDismissRequest = { onDismissDialog() }) {
                    WebAuthView(viewModel) { onDismissDialog() }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun AuthScreenPreview() {
    AuthScreenContent(
        showLoginDialog = false,
        onConnectClick = {},
        onDismissDialog = {}
    )
}
