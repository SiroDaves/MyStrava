package com.siro.mystrava

import android.os.*
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.siro.mystrava.presentation.screens.auth.AuthScreen
import com.siro.mystrava.presentation.screens.home.HomeScreen
import com.siro.mystrava.presentation.theme.*
import com.siro.mystrava.presentation.viewmodels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: HomeViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val code = intent?.data?.getQueryParameter("code")
        if (!code.isNullOrEmpty()) {
            Timber.d("Auth: code $code")
            viewModel.loginAthlete(code)
        }

        setContent {
            Material3Theme {
                val isLoggedIn by viewModel.isLoggedInStrava.observeAsState()
                val isLoggingIn by viewModel.loginInProgress.collectAsState()

                when {
                    isLoggingIn -> {
                        Box(
                            modifier = Modifier
                                .fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(64.dp),
                                strokeWidth = 6.dp
                            )
                        }
                    }
                    isLoggedIn == true -> {
                        HomeScreen(viewModel = viewModel)
                    }
                    else -> {
                        AuthScreen()
                    }
                }
            }
        }
    }
}
