package com.example.services

import android.app.IntentService
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat

class MyIntentService : IntentService(NAME) {


    @Deprecated("Deprecated in Java")
    override fun onCreate() {
        super.onCreate()
        log("OnCreate")
        setIntentRedelivery(true)
        createNotificationChanel()
        startForeground(NOTIFICATION_ID, createNotification())
    }
    @Deprecated("Deprecated in Java")
    override fun onDestroy() {
        super.onDestroy()
        log("Destroy")
    }

    private fun createNotificationChanel() {
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                CHANEL_ID,
                CHANEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(notificationChannel)
        } else {
            TODO("VERSION.SDK_INT < O")
        }
    }

    private fun createNotification(): Notification {
        return NotificationCompat.Builder(this, CHANEL_ID)
            .setContentTitle("Title")
            .setContentText("Text")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .build()
    }

    @Deprecated("Deprecated in Java")
    override fun onHandleIntent(p0: Intent?) {
        for (i in 0 until 3) {
            Thread.sleep(1000)
            log("Timer $i")
        }
    }

    private fun log(message: String) {
        Log.d("My", "MyForegroundService: $message")
    }

    companion object {
        private const val CHANEL_ID = "chanel_id"
        private const val CHANEL_NAME = "chanel_name"
        private const val NOTIFICATION_ID = 1
        private const val NAME = "MyIntentService"
        fun newIntent(context: Context): Intent {
            return Intent(context, MyIntentService::class.java)
        }
    }
}