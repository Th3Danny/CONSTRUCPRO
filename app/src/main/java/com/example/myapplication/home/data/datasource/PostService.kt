package com.example.myapplication.home.data.datasource

import com.example.myapplication.home.data.model.Post
import retrofit2.Response
import retrofit2.http.GET

interface PostService {
    @GET("posts") // Asegúrate de que coincide con tu endpoint real
    suspend fun getPosts(): Response<List<Post>>
}
