package com.example.myapplication

import android.app.Application
import android.content.Intent


class MyApplication : Application() {
    private var eventServiceRunning = false

    override fun onCreate() {
        super.onCreate()

        createNotificationChannel(this)



        if (!eventServiceRunning) {
            val intent = Intent(this, EventService::class.java)
            startService(intent)

            eventServiceRunning = true
        }
    }


}