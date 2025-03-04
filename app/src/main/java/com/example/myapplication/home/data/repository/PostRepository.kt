package com.example.myapplication.home.data.repository

import com.example.myapplication.core.network.RetrofitHelper
import com.example.myapplication.home.data.model.Post

class PostRepository {
    private val postService = RetrofitHelper.postService

    suspend fun getPosts(): Result<List<Post>> {
        return try {
            val response = postService.getPosts()
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
