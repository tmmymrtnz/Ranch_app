package com.example.myapplication.data.network.model

import com.google.gson.annotations.SerializedName


data class RoutineDevice (

    @SerializedName("id"   ) var id   : String? = null,
    @SerializedName("name" ) var name : String? = null,
    @SerializedName("type" ) var type : Type?   = Type(),
    @SerializedName("room" ) var room : Room?   = Room()

)