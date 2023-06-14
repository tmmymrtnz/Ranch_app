package com.example.myapplication.data.network.model

import com.google.gson.annotations.SerializedName


data class Args (

    @SerializedName("type"        ) var type        : String?  = null,
    @SerializedName("description" ) var description : String?  = null,

)
