package com.example.myapplication.oven

import com.example.myapplication.data.network.model.Device
import com.example.myapplication.data.network.model.DeviceList
import com.example.myapplication.data.network.model.Result
data class OvenUiState(

    val isLoading: Boolean = false,
    val message: String? = null,
    val device: Device? = null,

    val selectedGrillMode: String = device?.result?.state?.grill.toString(),
    val selectedConvMode:String = "off",
    val selectedHeatMode: String ="top",
    val Oventempeture: Int = 180,
    val OvenOn: Boolean = true,


)

val OvenUiState.hasError: Boolean get() = message != null