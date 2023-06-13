package com.example.myapplication.data.network.model

import com.google.gson.annotations.SerializedName


data class Room (

    @SerializedName("id"   ) var id   : String? = null,
    @SerializedName("name" ) var name : String? = null,
    @SerializedName("home" ) var home : Home?   = Home()

)
