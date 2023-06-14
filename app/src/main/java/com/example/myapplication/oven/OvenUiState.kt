package com.example.myapplication.oven

import com.example.myapplication.data.network.model.Result
data class OvenUiState(
    val selectedGrillMode: String ="Off",
    val selectedConvMode:String = "Off",
    val selectedHeatMode: String ="Top",
    val Oventempeture: Int = 180,
    val OvenOn: Boolean = true,

    val isLoading: Boolean = false,
    val message: String? = null,
    val device: Result? = null,
)

val OvenUiState.hasError: Boolean get() = message != null