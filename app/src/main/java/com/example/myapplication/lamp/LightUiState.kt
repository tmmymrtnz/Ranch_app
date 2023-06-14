package com.example.myapplication.lamp

import androidx.compose.ui.graphics.Color

data class LightUiState(
    val lightOn: Boolean = false,
    val brightness: Float = 0.5f,
    val lightColor: Color = Color.White

)
