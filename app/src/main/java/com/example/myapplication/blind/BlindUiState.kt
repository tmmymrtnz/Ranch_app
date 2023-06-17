package com.example.myapplication.blind

import com.example.myapplication.data.network.model.Device

data class BlindUiState (

    val isLoading: Boolean = false,
    val message: String? = null,
    val device: Device? = null,

    val blindOn: Boolean = false,
    val status: String = "closed",
    val blindLevel: Int = 0 /* pertenece al intervalo [0,100] */

)