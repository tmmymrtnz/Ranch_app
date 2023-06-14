package com.example.myapplication.data.network

import com.example.myapplication.data.network.model.DeviceList
import com.example.myapplication.data.network.model.DeviceTypesList
import com.example.myapplication.data.network.model.Result
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("devices/")
    suspend fun getDevices(): Response<DeviceList>

    @GET("devices/")
    suspend fun getADevice(@Query("deviceId")page: String): Response<Result>

    @GET("devicetypes/")
    suspend fun getDeviceTypes(): Response<DeviceTypesList>
}
