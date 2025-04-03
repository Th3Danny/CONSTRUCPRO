package com.valhallatech.civibridge.profile.data.datasource

import com.valhallatech.civibridge.profile.data.model.CertificationRequest
import com.valhallatech.civibridge.profile.data.model.CertificationResponse
import com.valhallatech.civibridge.profile.data.model.CompleteProfileResponse
import com.valhallatech.civibridge.profile.data.model.EducationRequest
import com.valhallatech.civibridge.profile.data.model.EducationResponse
import com.valhallatech.civibridge.profile.data.model.ProfessionalProfileRequest
import com.valhallatech.civibridge.profile.data.model.ProfileResponse
import com.valhallatech.civibridge.profile.data.model.SkillsRequest
import com.valhallatech.civibridge.profile.data.model.SkillsResponse
import com.valhallatech.civibridge.profile.data.model.WorkExperienceRequest
import com.valhallatech.civibridge.profile.data.model.WorkExperienceResponse
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