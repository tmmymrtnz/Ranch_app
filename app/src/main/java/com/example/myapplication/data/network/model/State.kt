package com.example.myapplication.data.network.model

import com.google.gson.annotations.SerializedName


data class State (

    @SerializedName("freezerTemperature" ) var freezerTemperature : Int?    = null,
    @SerializedName("temperature"        ) var temperature        : Int?    = null,
    @SerializedName("mode"               ) var mode               : String? = null

)