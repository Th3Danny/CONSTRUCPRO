package com.example.myapplication.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import com.example.myapplication.core.workers.WorkManagerScheduler

class ConnectivityReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        if (intent?.action == ConnectivityManager.CONNECTIVITY_ACTION) {
            val workManagerScheduler = WorkManagerScheduler()
            workManagerScheduler.schedulePendingApplicationsSync(context) // 📌 Iniciar sincronización al detectar internet
        }
    }
}
