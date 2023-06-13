package com.example.myapplication.data.network.model

import com.google.gson.annotations.SerializedName


data class DeviceList (

    @SerializedName("result" ) var result : List<Result> = listOf(),

)