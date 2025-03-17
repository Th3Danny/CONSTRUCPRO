package com.example.myapplication.core.service

import android.Manifest
import android.app.*
import android.content.Context
import android.content.Intent
import android.content.pm.ServiceInfo
import android.net.*
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresPermission
import androidx.core.app.NotificationCompat
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.myapplication.R
import com.example.myapplication.core.workers.SyncJobApplicationWorker

class NetworkMonitorService : Service() {

    private val CHANNEL_ID = "NetworkMonitorServiceChannel"
    private lateinit var connectivityManager: ConnectivityManager
    private lateinit var networkCallback: ConnectivityManager.NetworkCallback

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()

            startForeground(1, getNotification("Monitoreando conexión..."), ServiceInfo.FOREGROUND_SERVICE_TYPE_CONNECTED_DEVICE)



        connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        networkCallback = object : ConnectivityManager.NetworkCallback() {
            @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
            override fun onAvailable(network: Network) {
                if (isInternetAvailable()) {
                    Log.d("NetworkMonitorService", "📡 Internet detectado, ejecutando sincronización inmediata...")

                    val workRequest = OneTimeWorkRequestBuilder<SyncJobApplicationWorker>().build()
                    WorkManager.getInstance(applicationContext).enqueue(workRequest)
                }
            }

            override fun onLost(network: Network) {
                Log.d("NetworkMonitorService", "🚨 Se perdió la conexión a Internet.")
            }
        }

        val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()
        connectivityManager.registerNetworkCallback(networkRequest, networkCallback)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY // 🔹 Mantiene el servicio corriendo incluso si la app se cierra
    }

    override fun onDestroy() {
        super.onDestroy()
        connectivityManager.unregisterNetworkCallback(networkCallback)
        Log.d("NetworkMonitorService", "❌ Servicio detenido.")
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                CHANNEL_ID,
                "Monitoreo de Conexión",
                NotificationManager.IMPORTANCE_LOW
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager?.createNotificationChannel(serviceChannel)
        }
    }

    private fun getNotification(content: String): Notification {
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Monitoreo de Red")
            .setContentText(content)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .build()
    }

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    private fun isInternetAvailable(): Boolean {
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}
