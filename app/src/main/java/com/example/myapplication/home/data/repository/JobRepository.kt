package com.example.myapplication.home.data.repository

import android.util.Log
import com.example.myapplication.core.network.RetrofitHelper
import com.example.myapplication.home.data.model.Job


class JobRepository {
    private val jobService = RetrofitHelper.jobService

    suspend fun getJobs(): Result<List<Job>> {
        return try {
            val response = jobService.getJobs()
            if (response.isSuccessful) {
                val jobResponse = response.body()
                Log.d("JobRepository", "✅ Respuesta exitosa: ${jobResponse?.data}")
                if (jobResponse != null) Result.success(jobResponse.data)
                else Result.failure(Exception("Lista de trabajos vacía"))
            } else {
                val errorBody = response.errorBody()?.string()
                Log.e("JobRepository", "🚨 Error de servidor: $errorBody")
                Result.failure(Exception(errorBody ?: "Error desconocido"))
            }
        } catch (e: Exception) {
            Log.e("JobRepository", "🚨 Excepción al obtener trabajos: ${e.message}")
            Result.failure(e)
        }
    }
}


