package com.example.myapplication


import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.myapplication.devices.DeviceViewModel
import com.example.myapplication.oven.OvenViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL


class ShowNotificationReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val deviceId: String? = intent.getStringExtra(MyIntent.DEVICE_ID)
        val deviceName: String? = intent.getStringExtra(MyIntent.DEVICE_NAME)
        Log.d("EventService", "Show notification intent received {$deviceId)")

        sendCustomNotification(context, context.getString(R.string.app_name),context.getString(R.string.message, deviceName))
    }





}