package com.valhallatech.civibridge.profile.domain

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
import com.valhallatech.civibridge.profile.data.repository.ProfileRepository
import retrofit2.Response

class ProfileUseCase(private val repository: ProfileRepository) {

    suspend fun getCompleteProfile(userId: Int): Response<CompleteProfileResponse> {
        return repository.getCompleteProfile(userId)
    }

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