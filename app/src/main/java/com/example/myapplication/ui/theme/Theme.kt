package com.example.myapplication.ui.theme

import android.app.Activity
import android.os.Build
import android.util.Log
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = Blue50,
    secondary = Color.White,
    tertiary = Blue100,
    background = Blue70,
    scrim = Color.White,  //lo uso como color del texto
    tertiaryContainer = Blue100,  // para la nav bar
    outline = Color.Gray

)

private val LightColorScheme = lightColorScheme(
    primary =  Blue50,
    secondary = Blue100,
    tertiary = Blue70,
    background = Color.White,
    scrim = Color.Black,  //lo uso como color del texto
    tertiaryContainer = Color.White,  // para la nav bar
    outline = Color.Gray

)

@Composable
fun MyApplicationTheme(
    content: @Composable () -> Unit
) {
    val colorScheme = if (isSystemInDarkTheme()) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}



