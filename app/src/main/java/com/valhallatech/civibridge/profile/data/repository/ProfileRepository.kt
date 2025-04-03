package com.valhallatech.civibridge.profile.data.repository

import android.util.Log
import com.valhallatech.civibridge.core.network.RetrofitHelper.profileService
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

class ProfileRepository {

    suspend fun getCompleteProfile(userId: Int): Response<CompleteProfileResponse> {
            return profileService.getCompleteProfile(userId)
    }

    suspend fun postProfessionalProfile(profile: ProfessionalProfileRequest): Response<ProfileResponse> {
        Log.d("profileRepository", " Perfil enviado: $profile")
        return profileService.createProfessionalProfile(profile)
    }

    suspend fun postWorkExperience(profile: WorkExperienceRequest): Response<WorkExperienceResponse> {
        Log.d("profileRepository", " Experiencia enviadas: $profile")
        return profileService.workExperience(profile)
    }

    suspend fun postSkills(profile: SkillsRequest): Response<SkillsResponse> {
        Log.d("profileRepository", " Habilidades enviadas: $profile")
        return profileService.skills(profile)
    }

    suspend fun postEducation(profile: EducationRequest): Response<EducationResponse> {
        Log.d("profileRepository", " Educasion enviadas: $profile")
        return profileService.eduaction(profile)
    }

    suspend fun postCertification(profile: CertificationRequest): Response<CertificationResponse> {
        Log.d("profileRepository", "Certificado enviadas: $profile")
        return profileService.certifications(profile)
    }

}