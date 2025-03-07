package com.example.myapplication.job.data.model

data class Job(
    val id: Int,
    val title: String,
    val description: String,
    val location: String,
    val salary: Double,
    val application_count: Int,
    val like_count: Int
)

