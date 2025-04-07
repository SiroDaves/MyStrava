package com.siro.mystrava

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.Keep
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import com.siro.mystrava.presentation.auth.AuthScreen
import com.siro.mystrava.presentation.dashboard.StravaDashboard
import com.siro.mystrava.presentation.viewmodels.StravaDashboardViewModel
import com.siro.mystrava.presentation.theme.Material3Theme
import dagger.hilt.android.AndroidEntryPoint


@ExperimentalComposeUiApi
@ExperimentalFoundationApi
@Keep
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: StravaDashboardViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            Material3Theme(content = {
                val isLoggedIn by viewModel.isLoggedInStrava.observeAsState()
                var showLoginDialog by remember { mutableStateOf(false) }

                isLoggedIn?.let {
                    if (it) {
                        Scaffold(
                            content = { paddingValues ->
                                StravaDashboard(
                                    viewModel = viewModel,
                                    paddingValues = paddingValues
                                )
                            },
                        )
                    } else {
                        AuthScreen(
                            showLoginDialog = showLoginDialog,
                            onLoginClick = { showLoginDialog = true },
                            onDialogDismiss = { showLoginDialog = false },
                            viewModel = viewModel
                        )
                    }
                }
            })
        }
    }
}
