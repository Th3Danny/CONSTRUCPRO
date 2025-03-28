package com.example.myapplication.profile.data.datasource

import com.example.myapplication.profile.data.model.CertificationRequest
import com.example.myapplication.profile.data.model.CertificationResponse
import com.example.myapplication.profile.data.model.CompleteProfileResponse
import com.example.myapplication.profile.data.model.EducationRequest
import com.example.myapplication.profile.data.model.EducationResponse
import com.example.myapplication.profile.data.model.ProfessionalProfileRequest
import com.example.myapplication.profile.data.model.ProfileResponse
import com.example.myapplication.profile.data.model.SkillsRequest
import com.example.myapplication.profile.data.model.SkillsResponse
import com.example.myapplication.profile.data.model.WorkExperienceRequest
import com.example.myapplication.profile.data.model.WorkExperienceResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ProfileService {
    @GET("worker-profiles/user/{userId}/complete")
    suspend fun getCompleteProfile(@Path("userId") userId: Int): Response<CompleteProfileResponse>

    @POST("worker-profiles")
    suspend fun createProfessionalProfile(@Body profile: ProfessionalProfileRequest): Response<ProfileResponse>

    @POST("work-experiences")
    suspend fun workExperience(@Body profile: WorkExperienceRequest): Response<WorkExperienceResponse>

    @POST("skills")
    suspend fun skills(@Body profile: SkillsRequest): Response<SkillsResponse>

    @POST("educations")
    suspend fun eduaction(@Body profile: EducationRequest): Response<EducationResponse>

    @POST("certifications")
    suspend fun certifications(@Body profile: CertificationRequest): Response<CertificationResponse>
}