package com.example.myapplication.data.network.model

import com.google.gson.annotations.SerializedName


data class DeviceTypesList (

    @SerializedName("result" ) var result : List<DeviceType> = listOf()

)