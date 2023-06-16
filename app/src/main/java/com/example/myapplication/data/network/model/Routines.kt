package com.example.myapplication.data.network.model

import com.google.gson.annotations.SerializedName


data class Routines (

    @SerializedName("result" ) var result : ArrayList<RoutineType> = arrayListOf()

)