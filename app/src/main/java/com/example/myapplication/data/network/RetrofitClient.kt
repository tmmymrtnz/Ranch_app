package com.example.myapplication.data.network

import com.example.myapplication.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitClient {

    private var retrofit: Retrofit? = null

    init {
        try{
            println("API Base URL: ${BuildConfig.API_BASE_URL}")

            val httpLoggingInterceptor = HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY)

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build()

            retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
        }
        catch (e: Exception){
            e.printStackTrace()
        }
    }

    fun getApiService() : ApiService? = retrofit?.create(ApiService::class.java)

}