package com.example.myapplication.core.workers

import android.content.Context
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

class WorkManagerScheduler {
    fun schedulePendingApplicationsSync(context: Context) {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED) // ✅ Solo ejecuta si hay internet
            .build()

        val workRequest = OneTimeWorkRequestBuilder<SyncJobApplicationWorker>()
            .setConstraints(constraints)
            .setInitialDelay(5, TimeUnit.SECONDS) // Espera 5s antes de ejecutarse
            .build()

        WorkManager.getInstance(context).enqueue(workRequest)
    }
}
