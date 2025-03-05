package com.example.myapplication.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.home.domain.GetPostsUseCase


class PostViewModelFactory(private val getPostsUseCase: GetPostsUseCase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PostViewModel::class.java)) {
            return PostViewModel(getPostsUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
