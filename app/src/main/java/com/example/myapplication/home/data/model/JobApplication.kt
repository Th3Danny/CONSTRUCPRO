package com.example.myapplication.home.data.model

data class JobApplication(
    val id: Int,
    val applicant_id: Int,
    val applicant_email: String,
    val job_id: Int,
    val job_title: String,
    val status: String, // PENDING o ACCEPTED
    val applied_at: String
)

data class JobApplicationResponse(
    val data: List<JobApplication>
)

data class JobApplicationRequest(
    val job_id: Int,
    val applicant_id: Int
)