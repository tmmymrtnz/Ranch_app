package com.example.myapplication.data.network.model

import com.google.gson.annotations.SerializedName

data class Result (

    @SerializedName("id"    ) var id    : String? = null,
    @SerializedName("name"  ) var name  : String? = null,
    @SerializedName("type"  ) var type  : Type?   = Type(),
    @SerializedName("state" ) var state : State?  = State(),
    @SerializedName("room"  ) var room  : Room?   = Room(),
    @SerializedName("events") var events: List<Events> = listOf(),
    @SerializedName("actions") var actions: List<Actions> = listOf(),
    @SerializedName("powerUsage") var powerUsage: Int? = null,

)
