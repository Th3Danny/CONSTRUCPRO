package com.valhallatech.civibridge.profile.data.model



//Get-------------------------------------------------------------------------->
data class CompleteProfileResponse(
    val data: CompleteProfileData,
    val message: String,
    val success: Boolean,
    val http_status: String
)

data class CompleteProfileData(
    val profile: ProfessionalProfileData,
    val experiences: List<WorkExperienceData>,
    val educations: List<EducationData>,
    val skills: List<SkillsData>,
    val certifications: List<CertificationData>
)

//Post---------------------------------------------------------------->
data class ProfileResponse(
    val success: Boolean,
    val message: String,
    val data: ProfessionalProfileData
)

data class ProfessionalProfileData(
    val id: Int,
    val user_id: Int,
    val username: String,
    val name: String,
    val email: String,
    val headline: String,
    val about: String,
    val location: String,
    val contact_phone: String,
    val profile_imageUrl: String,
    val created_at: String?,
    val updated_at: String?
)


data class WorkExperienceResponse(
    val data: WorkExperienceData,
    val message: String,
    val success: Boolean,
    val http_status: String
)

data class WorkExperienceData(
    val id: Int,
    val title: String,
    val company: String,
    val location: String,
    val description: String,
    val is_current: Boolean,
    val start_date: String,
    val end_date: String?,
    val created_at: String,
    val updated_at: String
)

data class SkillsResponse(
    val success: Boolean,
    val message: String,
    val data: SkillsData
)

data class SkillsData(
    val id: Int,
    val profile_id: Int,
    val name: String,
    val proficiency: Int,
    val created_at: String?,
    val updated_at: String?
)

data class EducationResponse(
    val success: Boolean,
    val message: String,
    val data: EducationData
)

data class EducationData(
    val id: Int,
    val profile_id: Int,
    val institution: String,
    val degree: String,
    val field_of_study: String,
    val start_date: String,
    val end_date: String?,
    val description: String,
    val created_at: String?,
    val updated_at: String?
)

data class CertificationResponse(
    val success: Boolean,
    val message: String,
    val data: CertificationData
)

data class CertificationData(
    val id: Int,
    val profile_id: Int,
    val name: String,
    val issuing_organization: String,
    val issue_date: String,
    val expiration_date: String?,
    val credential_id: String,
    val credential_url: String,
    val created_at: String?,
    val updated_at: String?
)
