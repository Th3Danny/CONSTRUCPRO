package com.example.myapplication.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import androidx.compose.ui.unit.Constraints
import androidx.work.*
import androidx.work.WorkManager
import com.example.myapplication.core.workers.SyncJobApplicationWorker
import com.google.android.datatransport.cct.internal.NetworkConnectionInfo
import java.util.concurrent.TimeUnit

class ConnectivityReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        if (intent?.action == ConnectivityManager.CONNECTIVITY_ACTION) {
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkConnectionInfo.NetworkType.CONNECTED)
                .build()

            val syncRequest = OneTimeWorkRequestBuilder<SyncJobApplicationWorker>()
                .setConstraints(constraints)
                .build()

            WorkManager.getInstance(context).enqueue(syncRequest)
        }
    }
}
