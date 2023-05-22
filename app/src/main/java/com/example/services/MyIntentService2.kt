package com.example.services

import android.app.IntentService
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.JobIntentService
import androidx.core.app.JobIntentService.enqueueWork
import androidx.core.app.NotificationCompat

class MyIntentService2 : IntentService(NAME) {


    @Deprecated("Deprecated in Java")
    override fun onCreate() {
        super.onCreate()
        log("OnCreate")
        setIntentRedelivery(true)
    }
    @Deprecated("Deprecated in Java")
    override fun onDestroy() {
        super.onDestroy()
        log("Destroy")
    }


    @Deprecated("Deprecated in Java")
    override fun onHandleIntent(intent: Intent?) {
        val page = intent?.getIntExtra(PAGE, 0) ?: 0
        for (i in 0 until 3) {
            Thread.sleep(1000)
            log("Timer $i, $page")
        }
    }

    private fun log(message: String) {
        Log.d("My", "MyIntentService2: $message")
    }

    companion object {
        private const val NAME = "MyIntentService"
        private const val PAGE = "page"


        fun newIntent(context: Context, page: Int): Intent {
            return Intent(context, MyIntentService2::class.java).apply {
                putExtra(PAGE, page)
            }
        }
    }
}