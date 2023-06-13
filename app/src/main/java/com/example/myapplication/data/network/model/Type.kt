package com.example.myapplication.data.network.model

import com.google.gson.annotations.SerializedName


data class Type (

    @SerializedName("id"         ) var id         : String? = null,
    @SerializedName("name"       ) var name       : String? = null,
    @SerializedName("powerUsage" ) var powerUsage : Int?    = null

)
