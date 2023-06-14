package com.example.myapplication.data.network.model

import com.google.gson.annotations.SerializedName


data class Events (

    @SerializedName("name" ) var name : String? = null,
    @SerializedName("args" ) var args : Args?   = Args()

)
