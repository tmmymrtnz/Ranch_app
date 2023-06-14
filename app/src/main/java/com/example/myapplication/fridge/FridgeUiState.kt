package com.example.myapplication.fridge

data class FridgeUiState(
    val selectedFridgeMode: String = "Default",
    val fridgeTemp: Int = 5,
    val freezerTemp:Int = -15

)
