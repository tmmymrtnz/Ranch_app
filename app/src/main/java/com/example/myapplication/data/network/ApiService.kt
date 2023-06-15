package com.example.myapplication.data.network

import com.example.myapplication.data.network.model.Device
import com.example.myapplication.data.network.model.DeviceList
import com.example.myapplication.data.network.model.DeviceTypesList
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path


interface ApiService {

    @GET("devices/")
    suspend fun getDevices(): Response<DeviceList>

    @GET("devicetypes/")
    suspend fun getDeviceTypes(): Response<DeviceTypesList>

    @GET("devices/{id}")
    suspend fun getADevice(@Path("id")id: String): Response<Device>

    @PUT("devices/{id}/{actionName}")
    suspend fun makeAction(
        @Path("id")id: String, @Path("actionName")actionName: String, @Body actionParams: List<String>?
    ): Response<Device>
}
