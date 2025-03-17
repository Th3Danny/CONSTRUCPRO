package com.example.myapplication.core.workers

import android.content.Context
import androidx.compose.ui.unit.Constraints
import com.google.android.datatransport.cct.internal.NetworkConnectionInfo

class WorkManagerScheduler {
    fun schedulePendingApplicationsSync(context: Context) {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkConnectionInfo.NetworkType.CONNECTED) // ✅ Se ejecuta solo si hay internet
            .build()

        val workRequest = OneTimeWorkRequestBuilder<SyncPendingApplicationsWorker>()
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(context).enqueue(workRequest)
    }

}