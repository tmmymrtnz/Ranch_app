package com.example.myapplication.deviceTypes

import com.example.myapplication.data.network.model.DeviceTypesList


data class DeviceTypesUiState(
    val deviceTypes: DeviceTypesList? = null,
    val isLoading: Boolean = false,
    val message: String? = null
)

val DeviceTypesUiState.hasError: Boolean get() = message != null