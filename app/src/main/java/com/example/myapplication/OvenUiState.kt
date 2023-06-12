package com.example.myapplication

data class OvenUiState(
    val selectedGrillMode: String ="Off",
    val selectedConvMode:String = "Off",
    val selectedHeatMode: String ="Top",
    val Oventempeture: Int = 180,
    val OvenOn: Boolean = true,

)
