package com.example.myapplication.core.workers

import android.content.Context
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager

class WorkManagerScheduler {
    fun schedulePendingApplicationsSync(context: Context) {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED) // ✅ Se ejecuta solo si hay internet
            .build()

        val workRequest = OneTimeWorkRequestBuilder<SyncJobApplicationWorker>() // ✅ Asegurar que exista
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(context).enqueue(workRequest)
    }
}
