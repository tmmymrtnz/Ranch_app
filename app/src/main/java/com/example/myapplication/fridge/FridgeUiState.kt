package com.example.myapplication.fridge

import com.example.myapplication.data.network.model.Device
import com.example.myapplication.oven.OvenUiState

data class FridgeUiState(
    val selectedFridgeMode: String = "Default",
    val fridgeTemp: Int = 5,
    val freezerTemp:Int = -15,

    val isLoading: Boolean = false,
    val message: String? = null,
    val device: Device? = null,
)

val FridgeUiState.hasError: Boolean get() = message != null