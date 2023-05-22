package com.example.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.app.job.JobWorkItem
import android.content.ComponentName
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.example.services.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var id = 0
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private var page = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        with(binding) {
            btnService.setOnClickListener {
                startService(MyService.newIntent(this@MainActivity, 20))
                stopService(MyForegroundService.newIntent(this@MainActivity))
            }
            btnForeground.setOnClickListener {
                showNotification()
            }
            btnForeground.setOnClickListener {
                ContextCompat.startForegroundService(
                    this@MainActivity,
                    MyForegroundService.newIntent(
                        this@MainActivity
                    )
                )
            }
            btnIntent.setOnClickListener {
                ContextCompat.startForegroundService(
                    this@MainActivity,
                    MyIntentService.newIntent(
                        this@MainActivity
                    )
                )
            }
            btnJobScheduler.setOnClickListener {
                val componentName = ComponentName(this@MainActivity, MyJobService::class.java)

                val jobInfo =
                    JobInfo.Builder(MyJobService.JOB_ID, componentName)
                        .setRequiresCharging(true)
                        .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                        .build()

                val jobScheduler = getSystemService(JOB_SCHEDULER_SERVICE) as JobScheduler
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    val intent = MyJobService.newIntent(page++)
                    jobScheduler.enqueue(jobInfo, JobWorkItem(intent))
                } else {
                    startService(MyIntentService2.newIntent(this@MainActivity, page++))
                }
            }
            btnJobIntent.setOnClickListener {
                MyJobIntentService.enqueue(this@MainActivity, page++)
            }
        }
    }

    private fun showNotification() {
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
        val notification =
            NotificationCompat.Builder(this, CHANEL_ID)
                .setContentTitle("Title")
                .setContentText("Text")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .build()

        notificationManager.notify(id++, notification)
    }

    companion object {
        private const val CHANEL_ID = "chanel_id"
        private const val CHANEL_NAME = "chanel_name"
    }
}