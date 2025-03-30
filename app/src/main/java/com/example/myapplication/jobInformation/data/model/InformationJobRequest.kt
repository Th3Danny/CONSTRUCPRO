package com.example.myapplication.jobInformation.data.model
data class InformationJobRequest(
    val id: Int,
    val title: String,
    val description: String,
    val location: String,
    val salary: Double,
    val application_count: Int,
    val like_count: Int,
    val time_restant: Int
)
