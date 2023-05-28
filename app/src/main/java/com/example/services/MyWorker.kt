package com.example.services

import android.content.Context
import android.util.Log
import androidx.work.Constraints
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf

class MyWorker(context: Context, private val workerParameters: WorkerParameters) :
    Worker(context, workerParameters) {
    override fun doWork(): Result {
        log("doWork")
        val page = workerParameters.inputData.getInt(PAGE, 0)
        for (i in 0 until 3) {
            Thread.sleep(1000)
            log("Timer $i, $page")
        }
        return Result.success()
    }

    private fun log(message: String) {
        Log.d("My", "MyWorker: $message")
    }

    companion object {
        private const val PAGE = "page"
        const val WORK_NAME = "worker"

        fun makeRequest(page: Int): OneTimeWorkRequest {
            return OneTimeWorkRequestBuilder<MyWorker>()
                .apply {
                    setInputData(workDataOf(PAGE to page))
                    setConstraints(makeConstraints())
                }
                .build()
        }

        private fun makeConstraints() = Constraints.Builder()
            .setRequiresCharging(true)
            .build()

    }

}