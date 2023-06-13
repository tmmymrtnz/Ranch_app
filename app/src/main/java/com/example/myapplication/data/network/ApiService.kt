package com.example.myapplication.data.network

import com.example.myapplication.data.network.model.DeviceList
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("devices/")
    suspend fun getDevices(): Response<DeviceList>
}