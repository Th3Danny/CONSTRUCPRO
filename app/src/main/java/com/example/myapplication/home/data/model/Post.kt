package com.example.myapplication.home.data.model

data class Post(
    val id: String,
    val user: String,
    val content: String,
    val title: String,
    val timestamp: String,
    val likes: Int,
    val comments: Int
)
