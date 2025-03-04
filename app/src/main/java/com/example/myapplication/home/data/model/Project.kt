package com.example.myapplication.home.data.model

data class Project(
    val id: String,
    val name: String,
    val description: String,
    val deadline: String,
    val workers: List<String>,
    val status: String
)
