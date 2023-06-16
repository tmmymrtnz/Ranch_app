package com.example.myapplication.data.network.model

import com.google.gson.annotations.SerializedName


data class RoutinesResult (

    @SerializedName("result" ) var result : List<Any> = listOf()

)