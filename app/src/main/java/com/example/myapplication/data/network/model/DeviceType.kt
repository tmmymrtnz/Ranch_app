package com.example.myapplication.data.network.model

import com.google.gson.annotations.SerializedName


data class DeviceType (

    @SerializedName("id"         ) var id         : String?            = null,
    @SerializedName("name"       ) var name       : String?            = null,
    @SerializedName("powerUsage" ) var powerUsage : Int?               = null,
    @SerializedName("actions"    ) var actions    : List<Actions> = listOf(),
    @SerializedName("events"     ) var events     : List<Events>  = listOf()

)