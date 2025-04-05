package com.siro.mystrava.presentation.theme

import android.app.Activity
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.ViewCompat
// Oranges
private val Orange200 = Color(0xFFFFAB40)  // Light Orange
private val Orange400 = Color(0xFFFF9100)  // Medium Orange
private val Orange600 = Color(0xFFFB8C00)  // Darker Orange
private val Orange800 = Color(0xFFEF6C00)  // Deep Dark Orange

// Supporting Colors
private val Brown200 = Color(0xFF8D6E63)   // Muted Support
private val Brown400 = Color(0xFF6D4C41)   // Muted Darker
private val Red300 = Color(0xFFEA6D7E)     // Error Light
private val Red800 = Color(0xFFD00036)     // Error Dark
private val Cream = Color(0xFFFFF3E0)      // Soft Surface

private val DarkColorScheme = darkColorScheme(
    primary = Orange400,
    secondary = Brown400,
    error = Red300,
)

val LightColorScheme = lightColorScheme(
    primary = Orange600,
    onPrimary = Color.Black,
    secondary = Brown200,
    onSecondary = Color.Black,
    surface = Cream,
    onSurface = Color.Black,
    onBackground = Color.Black,
    error = Red800,
    onError = Color.White
)

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun MyStravaTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable() () -> Unit) {

    // Dynamic color is available on Android 12+
    val dynamicColor = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
    val colorScheme: ColorScheme = when {
        dynamicColor && darkTheme -> dynamicDarkColorScheme(LocalContext.current)
        dynamicColor && !darkTheme -> dynamicLightColorScheme(LocalContext.current)
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
//        typography = Typography,
//        shapes = Shapes,
        content = content
    )
}

@Composable
fun Material3Theme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            (view.context as Activity).window.statusBarColor = colorScheme.primary.toArgb()
            ViewCompat.getWindowInsetsController(view)?.isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography(),
        content = content
    )
}

@Composable
fun Material3WidgetTheme(
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    context: Context,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            dynamicLightColorScheme(context)
        }
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography(),
        content = content
    )
}