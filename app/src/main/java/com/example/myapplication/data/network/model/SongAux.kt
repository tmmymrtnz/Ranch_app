package com.example.myapplication.data.network.model

import com.google.gson.annotations.SerializedName

data class SongAux (
    @SerializedName("name"   ) var title   : String?           = null,
    @SerializedName("artist"   ) var artist   : String?           = null,
    @SerializedName("album"   ) var album   : String?           = null,
    @SerializedName("duration"   ) var duration   : String?           = null,
    @SerializedName("progress"   ) var progress   : String?           = null
)