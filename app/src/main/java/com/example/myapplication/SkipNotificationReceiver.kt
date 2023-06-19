package com.example.myapplication

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class SkipNotificationReceiver(private val notif: Boolean) : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action.equals(MyIntent.SHOW_NOTIFICATION) &&
            notif
        ) {
           // Log.d("EventService", "Skipping notification send ($deviceId)")
            abortBroadcast()
        }
    }


}