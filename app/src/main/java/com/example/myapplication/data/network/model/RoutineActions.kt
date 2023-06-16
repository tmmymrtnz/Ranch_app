package com.example.myapplication.data.network.model

import com.google.gson.annotations.SerializedName


data class RoutineActions (

    @SerializedName("device"     ) var device     : RoutineDevice?            = RoutineDevice(),
    @SerializedName("actionName" ) var actionName : String?            = null,
    @SerializedName("params"     ) var params     : ArrayList<Any> = arrayListOf(),

)