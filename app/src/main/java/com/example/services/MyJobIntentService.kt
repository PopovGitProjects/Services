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
import androidx.core.app.NotificationCompat

class MyJobIntentService : JobIntentService() {


    @Deprecated("Deprecated in Java")
    override fun onCreate() {
        super.onCreate()
        log("OnCreate")
    }
    @Deprecated("Deprecated in Java")
    override fun onDestroy() {
        super.onDestroy()
        log("Destroy")
    }

    override fun onHandleWork(intent: Intent) {
        val page = intent.getIntExtra(PAGE, 0)
        for (i in 0 until 3) {
            Thread.sleep(1000)
            log("Timer $i, $page")
        }
    }

    private fun log(message: String) {
        Log.d("My", "MyJobIntentService: $message")
    }

    companion object {
        private const val PAGE = "page"
        private const val JOB_ID = 1

        fun enqueue(context: Context, page: Int){
            enqueueWork(context,
                MyJobIntentService::class.java,
                JOB_ID,
                newIntent(context, page)
            )
        }
        private fun newIntent(context: Context, page: Int): Intent {
            return Intent(context, MyJobIntentService::class.java).apply {
                putExtra(PAGE, page)
            }
        }
    }
}