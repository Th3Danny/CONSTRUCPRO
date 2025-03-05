package com.example.myapplication.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.home.domain.GetPostsUseCase
import com.example.myapplication.home.data.model.Post


class PostViewModel(private val getPostsUseCase: GetPostsUseCase) : ViewModel() {
    private val _posts = MutableLiveData<List<Post>>()
    val posts: LiveData<List<Post>> = _posts

    init {
        fetchPosts()  // 🔹 Cargar datos al iniciar
    }

    private fun fetchPosts() {
        viewModelScope.launch {
            val result = getPostsUseCase()
            result.onSuccess { posts ->
                _posts.value = posts
            }.onFailure {
                _posts.value = emptyList()
            }
        }
    }
}
