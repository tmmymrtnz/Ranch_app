package com.example.myapplication

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

// Create a notification channel
fun createNotificationChannel(context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        Log.d("Ranch","Notification Channel Created")
        val channelId = "Ranch"
        val channelName = "Ranch"
        val channelDescription = "Notifications"
        val importance = NotificationManager.IMPORTANCE_HIGH

        val channel = NotificationChannel(channelId, channelName, importance)
        channel.description = channelDescription

        val notificationManager =
            context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}
fun sendCustomNotification(context: Context, title: String, content: String) {
    Log.d("Ranch", "Sending Custom Notification")



    // Build the notification
    val builder = NotificationCompat.Builder(context, "Ranch")
        .setSmallIcon(R.drawable.logo_notext)
        .setContentTitle(title)
        .setContentText(content)

    // Create a default PendingIntent
    val intent = context.packageManager.getLaunchIntentForPackage(context.packageName)
    val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)

    // Set the default PendingIntent for the notification
    builder.setContentIntent(pendingIntent)

    // Get a unique notification ID
    val notificationId = title.hashCode()

    // Post the notification
    val notificationManager = NotificationManagerCompat.from(context)
    if (ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.POST_NOTIFICATIONS
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        // TODO: Request permission or handle missing permission
        return
    }
    notificationManager.notify(notificationId, builder.build())
}

// Crea un default PendingIntent
private fun getDefaultPendingIntent(context: Context): PendingIntent {
    // TODO: Replace with your desired action
    val intent = context.packageManager.getLaunchIntentForPackage(context.packageName)
    return PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
}


