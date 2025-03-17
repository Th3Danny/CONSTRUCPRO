package com.example.myapplication.job.data.repository

import android.content.Context
import android.util.Log
import com.example.myapplication.core.data.local.dao.PendingJobApplicationDao
import com.example.myapplication.core.data.local.entities.PendingJobApplicationEntity
import com.example.myapplication.core.network.RetrofitHelper
import com.example.myapplication.job.data.model.Job
import com.example.myapplication.job.data.model.JobApplication
import com.example.myapplication.job.data.model.JobApplicationRequest
import com.example.myapplication.job.data.model.JobApplicationResponse
import com.example.myapplication.job.data.model.JobResponse
import retrofit2.HttpException
import java.io.IOException


class JobRepository(
    private val context: Context,
    private val pendingJobApplicationDao: PendingJobApplicationDao
) {
    private val jobService = RetrofitHelper.jobService
    private val  jobServicePost = RetrofitHelper.JobServicePost
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


    suspend fun applyForJob(jobId: Int, applicantId: Int): Result<Boolean> {
        return try {
            val request = JobApplicationRequest(jobId, applicantId)
            val response = jobServicePost.applyToJob(request)

            if (!response.isSuccessful) {
                throw HttpException(response)
            }

            Result.success(true) // ✅ Devuelve `Result<Boolean>` en caso de éxito
        } catch (e: IOException) {
            Log.e("JobRepository", "⚠ No hay conexión a internet, guardando en Room...")
            savePendingApplication(jobId, applicantId)
            Result.success(false) // ✅ Indica que la aplicación fue almacenada localmente
        } catch (e: HttpException) {
            Log.e("JobRepository", "❌ Error en la API: ${e.message()}")
            Result.failure(e) // ✅ Devuelve un fallo en caso de error de API
        }
    }


    private suspend fun savePendingApplication(jobId: Int, applicantId: Int) {
        val pendingApplication = PendingJobApplicationEntity(jobId = jobId, applicantId = applicantId)
        pendingJobApplicationDao.insertPendingApplication(pendingApplication)
        Log.d("JobRepository", "📌 Aplicación guardada en Room para sincronización futura.")
    }

    suspend fun getAllPendingApplications(): List<PendingJobApplicationEntity> {
        return pendingJobApplicationDao.getAllPendingApplications()
    }
}


