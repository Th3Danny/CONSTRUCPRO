package com.example.myapplication.home.domain

import com.example.myapplication.home.data.model.Post
import com.example.myapplication.home.data.repository.PostRepository

class GetPostsUseCase(private val repository: PostRepository) {
    suspend operator fun invoke(): Result<List<Post>> {
        return repository.getPosts()
    }
}
