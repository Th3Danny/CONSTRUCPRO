package com.example.myapplication.project.data.repository

import com.example.myapplication.core.network.RetrofitHelper
import com.example.myapplication.project.data.model.Project

class ProjectRepository {
    private val projectService = RetrofitHelper.projectService

    suspend fun getProjects(): Result<List<Project>> {
        return try {
            val response = projectService.getProjects()
            if (response.isSuccessful) {
                response.body()?.let {
                    Result.success(it)
                } ?: Result.failure(Exception("Respuesta vacía del servidor"))
            } else {
                Result.failure(Exception(response.errorBody()?.string() ?: "Error desconocido"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
