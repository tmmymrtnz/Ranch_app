package com.example.myapplication.data.network.model

import com.google.gson.annotations.SerializedName

data class Home (

    @SerializedName("id"   ) var id   : String? = null,
    @SerializedName("name" ) var name : String? = null

)