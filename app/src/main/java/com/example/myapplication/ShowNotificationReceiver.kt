package com.example.myapplication


import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log


class ShowNotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val deviceId: String? = intent.getStringExtra(MyIntent.DEVICE_ID)
        Log.d(TAG, "Show notification intent received {$deviceId)")

        sendCustomNotification(context, deviceId!!)
    }


    companion object {
        private const val TAG = "ShowNotificationReceiver"
    }
}