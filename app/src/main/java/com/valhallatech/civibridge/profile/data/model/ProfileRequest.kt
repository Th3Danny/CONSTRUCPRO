package com.valhallatech.civibridge.profile.data.model

import com.google.gson.annotations.SerializedName
import java.time.LocalDate

data class ProfessionalProfileRequest(
    val user_id: Int,
    val headline: String,
    val about: String,
    val location: String,
    val contact_phone: String,
    val profile_imageUrl: String
)

data class WorkExperienceRequest(
    val profile_id: Int,
    val title: String,
    val company: String,
    val location: String,
    val description: String,
    val is_current: Boolean,
    val start_date: LocalDate,
    @SerializedName("end_date")
    val end_date: LocalDate? = null

)

data class SkillsRequest(
    val profile_id: Int,
    val name: String,
    val proficiency: Int,
)

data class EducationRequest(
    val profile_id: Int,
    val institution: String,
    val degree: String,
    val field_of_study: String,
    val start_date: LocalDate,
    val end_date: LocalDate? = null,
    val description: String
)

data class CertificationRequest(
    val profile_id: Int,
    val name: String,
    val issuing_organization: String,
    val issue_date: LocalDate,
    val expiration_date: LocalDate? = null,
    val credential_id: String,
    val credential_url: String
)



