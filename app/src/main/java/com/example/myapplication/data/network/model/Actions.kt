package com.example.myapplication.data.network.model

import com.google.gson.annotations.SerializedName

data class Actions (

    @SerializedName("name"   ) var name   : String?           = null,
    @SerializedName("params" ) var params : ArrayList<Params> = arrayListOf(),
    @SerializedName("return" ) var returns : Return?           = Return()

)