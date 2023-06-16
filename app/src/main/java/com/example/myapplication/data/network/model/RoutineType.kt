package com.example.myapplication.data.network.model

import com.google.gson.annotations.SerializedName


data class RoutineType (

    @SerializedName("id"      ) var id      : String?            = null,
    @SerializedName("name"    ) var name    : String?            = null,
    @SerializedName("actions" ) var actions : List<RoutineActions> = listOf()

)