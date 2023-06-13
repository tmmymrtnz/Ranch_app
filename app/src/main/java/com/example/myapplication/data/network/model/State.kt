package com.example.myapplication.data.network.model

import com.google.gson.annotations.SerializedName


data class State (

    @SerializedName("freezerTemperature" ) var freezerTemperature : Int?    = null,
    @SerializedName("temperature"        ) var temperature        : Int?    = null,
    @SerializedName("mode"               ) var mode               : String? = null,
    @SerializedName("status"             ) var status      : String? = null,
    @SerializedName("heat"               ) var heat        : String? = null,
    @SerializedName("grill"              ) var grill       : String? = null,
    @SerializedName("convection"         ) var convection  : String? = null,
    @SerializedName("level"              ) var level        : Int?    = null,
    @SerializedName("currentLevel"       ) var currentLevel : Int?    = null,
    @SerializedName("volume"             ) var volume : Int?    = null,
    @SerializedName("genre"              ) var genre  : String? = null,
    @SerializedName("color"              ) var color      : String? = null,
    @SerializedName("brightness"         ) var brightness : Int?    = null

)