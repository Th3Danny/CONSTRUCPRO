package com.example.myapplication.home.data.datasource

import com.example.myapplication.home.data.model.Post
import retrofit2.Response
import retrofit2.http.GET

interface PostService {
    @GET("/posts")
    suspend fun getPosts(): Response<List<Post>>
}
