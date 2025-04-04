package com.siro.mystrava

import android.os.*
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import com.siro.mystrava.presentation.screens.auth.AuthScreen
import com.siro.mystrava.presentation.screens.dashboard.DashboardScreen
import com.siro.mystrava.presentation.theme.*
import com.siro.mystrava.presentation.viewmodels.DashboardViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: DashboardViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Material3Theme {
                val isLoggedIn by viewModel.isLoggedInStrava.observeAsState()

                isLoggedIn?.let { loggedIn ->
                    if (loggedIn) {
                        Scaffold(
                            content = { paddingValues ->
                                DashboardScreen(
                                    viewModel = viewModel,
                                    paddingValues = paddingValues
                                )
                            },
                        )
                    } else {
                        AuthScreen(viewModel)
                    }
                }
            }
        }
    }
}
