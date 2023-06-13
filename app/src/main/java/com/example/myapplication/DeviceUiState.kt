package com.example.myapplication

import com.example.myapplication.data.network.model.DeviceList

data class DeviceUiState(
    val devices: DeviceList? = null,
    val isLoading: Boolean = false,
    val message: String? = null
)

val DeviceUiState.hasError: Boolean get() = message != null