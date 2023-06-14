package com.example.myapplication.data.network.model

import com.google.gson.annotations.SerializedName


data class Params (

    @SerializedName("name"        ) var name        : String? = null,
    @SerializedName("type"        ) var type        : String? = null,
    @SerializedName("description" ) var description : String? = null,
    @SerializedName("minValue"    ) var minValue    : Any?    = null,
    @SerializedName("maxValue"    ) var maxValue    : Any?    = null,
    @SerializedName("supportedValues" ) var supportedValues : List<String> = listOf(),


)
