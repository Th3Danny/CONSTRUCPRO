package com.example.myapplication.jobInformation.data.model

data class InformationJobRequest(
    val id: Int,
    val title: String,
    val description: String,
    val location: String,
    val salary: Double,
    val application_count: Int,
    val like_count: Int,
    val time_restant: Int,
    val companyPhone: String? = null,
    val companyName: String? = null
)


data class ApiResponse<T>(
    val data: T?,
    val message: String?,
    val success: Boolean,
    val http_status: String?
)
