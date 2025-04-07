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
import com.siro.mystrava.presentation.auth.AuthScreen
import com.siro.mystrava.presentation.dashboard.HomeScreen
import com.siro.mystrava.presentation.viewmodels.HomeViewModel
import com.siro.mystrava.presentation.theme.Material3Theme
import dagger.hilt.android.AndroidEntryPoint


@ExperimentalComposeUiApi
@ExperimentalFoundationApi
@Keep
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: HomeViewModel by viewModels()

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
                        HomeScreen( viewModel = viewModel )
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
