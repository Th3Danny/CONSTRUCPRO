package com.example.myapplication.home.data.repository

import android.content.Context
import android.util.Log
import com.example.myapplication.core.network.RetrofitHelper
import com.example.myapplication.home.data.model.Job
import com.example.myapplication.home.data.model.JobApplication
import com.example.myapplication.home.data.model.JobApplicationRequest
import com.example.myapplication.home.data.model.JobApplicationResponse
import com.example.myapplication.home.data.model.JobResponse


class JobRepository(private val context: Context) {
    private val jobService = RetrofitHelper.jobService
    private val jobServicePost = RetrofitHelper.JobServicePost
    suspend fun getJobs(): Result<List<Job>> {
        return try {
            val sharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
            val userId = sharedPreferences.getInt("userId", -1)

            if (userId == -1) {
                Log.e("JobRepository", "🚨 No se encontró userId en SharedPreferences")
                return Result.failure(Exception("Usuario no autenticado"))
            }

            Log.d("JobRepository", "📡 Obteniendo trabajos para userId: $userId")

            val response = jobService.getJobs(userId)

            if (response.isSuccessful) {
                val jobResponse: JobResponse? = response.body()
                if (jobResponse != null) {
                    Log.d("JobRepository", "✅ Trabajos obtenidos: ${jobResponse.data.size} para userId: $userId")
                    return Result.success(jobResponse.data)
                } else {
                    Log.e("JobRepository", "⚠ Respuesta vacía del servidor para userId: $userId")
                    return Result.failure(Exception("Respuesta vacía del servidor"))
                }
            } else {
                Log.e("JobRepository", "⚠ Error en la respuesta de la API para userId: $userId - ${response.errorBody()?.string()}")
                return Result.failure(Exception("Error en la respuesta de la API"))
            }
        } catch (e: Exception) {
            Log.e("JobRepository", "🚨 Excepción al obtener trabajos para userId: ${e.message}")
            return Result.failure(e)
        }
    }

    suspend fun getPendingJobs(): Result<List<JobApplication>> {
        return try {
            val sharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
            val userId = sharedPreferences.getInt("userId", -1)

            if (userId == -1) {
                Log.e("JobRepository", "🚨 No se encontró userId en SharedPreferences")
                return Result.failure(Exception("Usuario no autenticado"))
            }

            Log.d("JobRepository", "📡 Obteniendo trabajos pendientes para userId: $userId")

            val response = jobService.getPendingApplications(userId)

            if (response.isSuccessful) {
                val pendingJobsResponse: JobApplicationResponse? = response.body()
                if (pendingJobsResponse != null) {
                    Log.d("JobRepository", "✅ Trabajos pendientes obtenidos: ${pendingJobsResponse.data.size}")
                    return Result.success(pendingJobsResponse.data)
                } else {
                    Log.e("JobRepository", "⚠ Respuesta vacía del servidor")
                    return Result.failure(Exception("Respuesta vacía del servidor"))
                }
            } else {
                Log.e("JobRepository", "⚠ Error en la respuesta de la API: ${response.errorBody()?.string()}")
                return Result.failure(Exception("Error en la respuesta de la API"))
            }
        } catch (e: Exception) {
            Log.e("JobRepository", "🚨 Excepción al obtener trabajos pendientes: ${e.message}")
            return Result.failure(e)
        }
    }

    suspend fun getAcceptedJobs(): Result<List<JobApplication>> {
        return try {
            val sharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
            val userId = sharedPreferences.getInt("userId", -1)

            if (userId == -1) {
                Log.e("JobRepository", "🚨 No se encontró userId en SharedPreferences")
                return Result.failure(Exception("Usuario no autenticado"))
            }

            Log.d("JobRepository", "📡 Obteniendo trabajos aceptados para userId: $userId")

            val response = jobService.getAcceptedApplications(userId)

            if (response.isSuccessful) {
                val acceptedJobsResponse: JobApplicationResponse? = response.body()
                if (acceptedJobsResponse != null) {
                    Log.d("JobRepository", "✅ Trabajos aceptados obtenidos: ${acceptedJobsResponse.data.size}")
                    return Result.success(acceptedJobsResponse.data)
                } else {
                    Log.e("JobRepository", "⚠ Respuesta vacía del servidor")
                    return Result.failure(Exception("Respuesta vacía del servidor"))
                }
            } else {
                Log.e("JobRepository", "⚠ Error en la respuesta de la API: ${response.errorBody()?.string()}")
                return Result.failure(Exception("Error en la respuesta de la API"))
            }
        } catch (e: Exception) {
            Log.e("JobRepository", "🚨 Excepción al obtener trabajos aceptados: ${e.message}")
            return Result.failure(e)
        }
    }


    suspend fun applyForJob(jobId: Int): Result<Boolean> {
        return try {
            val sharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
            val userId = sharedPreferences.getInt("userId", -1)

            if (userId == -1) {
                Log.e("JobRepository", "🚨 No se encontró userId en SharedPreferences")
                return Result.failure(Exception("Usuario no autenticado"))
            }

            Log.d("JobRepository", "📡 Aplicando a trabajo con jobId: $jobId para userId: $userId")

            val requestBody = JobApplicationRequest(
                job_id = jobId,
                applicant_id = userId
            )

            val response = jobServicePost.applyToJob(requestBody)

            if (response.isSuccessful) {
                Log.d("JobRepository", "✅ Aplicación enviada con éxito para jobId: $jobId")
                return Result.success(true)
            } else {
                Log.e("JobRepository", "⚠ Error al aplicar al trabajo: ${response.errorBody()?.string()}")
                return Result.failure(Exception("Error al aplicar al trabajo"))
            }
        } catch (e: Exception) {
            Log.e("JobRepository", "🚨 Excepción al aplicar a trabajo: ${e.message}")
            return Result.failure(e)
        }
    }
}


