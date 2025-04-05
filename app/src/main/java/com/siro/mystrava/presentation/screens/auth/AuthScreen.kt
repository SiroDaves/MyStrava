package com.siro.mystrava.presentation.screens.auth

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.siro.mystrava.BuildConfig.STRAVA_CLIENT_ID
import com.siro.mystrava.R
import com.siro.mystrava.core.utils.ApiConstants
import com.siro.mystrava.presentation.theme.*

@Composable
fun AuthScreen() {
    val context = LocalContext.current
    val intentUri = Uri.parse(ApiConstants.Uri.AUTH)
        .buildUpon()
        .appendQueryParameter("client_id", STRAVA_CLIENT_ID.toString())
        .appendQueryParameter("redirect_uri", ApiConstants.Uri.REDIRECT)
        .appendQueryParameter("response_type", "code")
        .appendQueryParameter("approval_prompt", "auto")
        .appendQueryParameter("scope", "activity:write,read")
        .build()
    AuthScreenContent(
        onConnectClick = {
            val intent = Intent(Intent.ACTION_VIEW, intentUri)
            context.startActivity(intent)
        }
    )
}

@Composable
fun AuthScreenContent(onConnectClick: () -> Unit ) {
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
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AuthScreenPreview() {
    AuthScreenContent(
        onConnectClick = {},
    )
}
