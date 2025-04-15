package com.siro.mystrava

import android.os.*
import androidx.activity.*
import androidx.activity.compose.setContent
import androidx.annotation.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.glance.LocalContext
import com.siro.mystrava.presentation.navigation.AppNavHost
import com.siro.mystrava.presentation.screens.auth.AuthScreen
import com.siro.mystrava.presentation.viewmodels.*
import com.siro.mystrava.presentation.theme.MyStravaTheme
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalComposeUiApi
@ExperimentalFoundationApi
@Keep
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val homeViewModel: HomeViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyStravaTheme(
                content = {
                    val isLoggedIn by homeViewModel.isLoggedInStrava.observeAsState()
                    var showLoginDialog by remember { mutableStateOf(false) }

                    isLoggedIn?.let {
                        if (it) {
                            AppNavHost(homeViewModel = homeViewModel)
                        } else {
                            AuthScreen(
                                showLoginDialog = showLoginDialog,
                                onLoginClick = { showLoginDialog = true },
                                onDialogDismiss = { showLoginDialog = false },
                                viewModel = homeViewModel
                            )
                        }
                    }
                },
            )
        }
    }
}
