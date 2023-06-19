package com.example.myapplication.data.network.model

import com.google.gson.annotations.SerializedName

data class Song (
    @SerializedName("name"   ) var title   : String?           = null,
    @SerializedName("name"   ) var artist   : String?           = null,
    @SerializedName("name"   ) var album   : String?           = null,
    @SerializedName("name"   ) var duration   : Int?           = null,
    @SerializedName("name"   ) var progress   : Int?           = null
)