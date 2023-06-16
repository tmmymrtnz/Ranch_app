package com.example.myapplication.lamp

import androidx.compose.ui.graphics.Color
import com.example.myapplication.data.network.model.Device
import com.example.myapplication.oven.OvenUiState

data class LightUiState(
    val lightOn: Boolean = false,
    val brightness: Int = 10,
    val lightColor: String = "#FFFFFF",

    val isLoading: Boolean = false,
    val message: String? = null,
    val device: Device? = null,
)

val LightUiState.hasError: Boolean get() = message != null