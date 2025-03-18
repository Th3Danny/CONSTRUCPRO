package com.example.myapplication.core.workers

import android.content.Context
import android.util.Log
import androidx.work.*
import com.example.myapplication.core.data.local.AppDatabase
import com.example.myapplication.job.data.repository.JobRepository

class SyncJobApplicationWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    private val database = AppDatabase.getDatabase(context)
    private val repository = JobRepository(context, database.pendingJobApplicationDao())

    override suspend fun doWork(): Result {
        Log.d("SyncWorker", " Iniciando sincronización en segundo plano...")

        val pendingApplications = repository.getAllPendingApplications()
        if (pendingApplications.isEmpty()) {
            Log.d("SyncWorker", " No hay aplicaciones pendientes.")
            return Result.success()
        }

        for (application in pendingApplications) {
            try {
                repository.applyForJob(application.jobId, application.applicantId)
                database.pendingJobApplicationDao().deletePendingApplication(application.id)
                Log.d("SyncWorker", " Aplicación sincronizada para jobId: ${application.jobId}")
            } catch (e: Exception) {
                Log.e("SyncWorker", " Error al sincronizar jobId: ${application.jobId}, reintentando luego.")
                return Result.retry() //  Reintenta si falla
            }
        }
        return Result.success()
    }
}
