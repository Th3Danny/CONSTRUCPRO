package com.example.myapplication.receiver


import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.util.Log
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.myapplication.core.workers.SyncJobApplicationWorker

class NetworkMonitor(private val context: Context) {

    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            if (isInternetAvailable()) {
                Log.d("NetworkMonitor", "📡 Internet detectado, iniciando sincronización inmediata...")

                // 🔹 Ejecutar la sincronización en WorkManager
                val workRequest = OneTimeWorkRequestBuilder<SyncJobApplicationWorker>().build()
                WorkManager.getInstance(context).enqueue(workRequest)
            }
        }

        override fun onLost(network: Network) {
            Log.d("NetworkMonitor", "🚨 Se perdió la conexión a Internet.")
        }
    }

    fun startMonitoring() {
        val networkRequest = android.net.NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()
        connectivityManager.registerNetworkCallback(networkRequest, networkCallback)
        Log.d("NetworkMonitor", "🔍 Monitoreo de conexión iniciado.")
    }

    fun stopMonitoring() {
        connectivityManager.unregisterNetworkCallback(networkCallback)
        Log.d("NetworkMonitor", "❌ Monitoreo de conexión detenido.")
    }

    private fun isInternetAvailable(): Boolean {
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}
