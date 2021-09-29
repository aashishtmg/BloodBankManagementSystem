package com.example.bloodbankmanagementsystem.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build

class NotificationsChannels (val context: Context){
    val channel_1: String = "Channel1"
    val channel_2: String = "Channel2"

    fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel1 = NotificationChannel(
                channel_1,
                "Channel 1",
                NotificationManager.IMPORTANCE_HIGH
            )
            channel1.description="This is channel 1"

            val channel2 = NotificationChannel(
                channel_2,
                "Channel 2",
                NotificationManager.IMPORTANCE_LOW
            )
            channel2.description="This is channel 2"

            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            notificationManager.createNotificationChannel(channel1)
            notificationManager.createNotificationChannel(channel2)
        }
    }
}