package com.example.myapplication.data.network.model

import com.google.gson.annotations.SerializedName


data class Return (

    @SerializedName("type"        ) var type        : String? = null,
    @SerializedName("description" ) var description : String? = null,
    @SerializedName("example"     ) var example     : Any?    = null

)
