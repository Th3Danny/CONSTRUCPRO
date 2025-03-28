package com.example.myapplication.profile.domain

import com.example.myapplication.profile.data.model.CertificationRequest
import com.example.myapplication.profile.data.model.CertificationResponse
import com.example.myapplication.profile.data.model.EducationRequest
import com.example.myapplication.profile.data.model.EducationResponse
import com.example.myapplication.profile.data.model.ProfessionalProfileRequest
import com.example.myapplication.profile.data.model.ProfileResponse
import com.example.myapplication.profile.data.model.SkillsRequest
import com.example.myapplication.profile.data.model.SkillsResponse
import com.example.myapplication.profile.data.model.WorkExperienceRequest
import com.example.myapplication.profile.data.model.WorkExperienceResponse
import com.example.myapplication.profile.data.repository.ProfileRepository
import retrofit2.Response

class ProfileUseCase(private val repository: ProfileRepository) {
    suspend fun submitProfessionalProfile(profile: ProfessionalProfileRequest): Response<ProfileResponse> {
        return repository.postProfessionalProfile(profile)
    }

    suspend fun submitWorkExperience(profile: WorkExperienceRequest): Response<WorkExperienceResponse> {
        return repository.postWorkExperience(profile)
    }

    suspend fun submitSkills(profile: SkillsRequest): Response<SkillsResponse> {
        return repository.postSkills(profile)
    }

    suspend fun submitEducation(profile: EducationRequest): Response<EducationResponse> {
        return repository.postEducation(profile)
    }

    suspend fun submitCertification(profile: CertificationRequest): Response<CertificationResponse> {
        return repository.postCertification(profile)
    }
}