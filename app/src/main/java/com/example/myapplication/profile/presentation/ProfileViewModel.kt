package com.example.myapplication.profile.presentation

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.myapplication.profile.data.model.ProfessionalProfileRequest
import androidx.lifecycle.viewModelScope
import com.example.myapplication.profile.data.model.CertificationRequest
import com.example.myapplication.profile.data.model.EducationRequest
import com.example.myapplication.profile.data.model.SkillsRequest
import com.example.myapplication.profile.data.model.WorkExperienceRequest
import com.example.myapplication.profile.domain.ProfileUseCase
import kotlinx.coroutines.launch

class ProfileViewModel(private val useCase: ProfileUseCase) : ViewModel() {
    val isProfileSaved = mutableStateOf(false)
    fun submitProfessionalProfile(profile: ProfessionalProfileRequest) {
        viewModelScope.launch {

            try {
                val response = useCase.submitProfessionalProfile(profile)
                Log.d("Profile", "Perfil laboral creada correctamente: ${response.body()}")
                if (response.isSuccessful) {
                    isProfileSaved.value = true
                } else {
                    Log.e("ProfileViewModel", "Error: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("ProfileViewModelProfile", "Excepción: ${e.message}")
            }
        }
    }

    fun submitWorkExperience(profile: WorkExperienceRequest) {
        viewModelScope.launch {
            try {
                val response = useCase.submitWorkExperience(profile)

                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        Log.d("WorkExperience", " Experiencia laboral creada correctamente:")
                        Log.d("WorkExperience", body.toString())
                    } else {
                        Log.w("WorkExperience", "La respuesta fue exitosa pero el cuerpo está vacío (null).")
                    }
                    isProfileSaved.value = true
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("ProfileViewModel", " Error del servidor: $errorBody")
                }
            } catch (e: Exception) {
                Log.e("ProfileViewModelWorkExperience", "Excepción en submitWorkExperience: ${e.message}")
            }
        }
    }


    fun submitSkills(profile: SkillsRequest) {
        viewModelScope.launch {
            try {
                val response = useCase.submitSkills(profile)
                if (response.isSuccessful) {
                    Log.d("Skills", "Habilidades creada correctamente: ${response.body()}")
                    Log.d("WorkExperience", response.toString())
                    isProfileSaved.value = true
                } else {
                    Log.e("ProfileViewModel", "Error: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("ProfileViewModelSkills", "Excepción: ${e.message}")
            }
        }
    }

    fun submitEducation(profile: EducationRequest) {
        viewModelScope.launch {
            try {
                val response = useCase.submitEducation(profile)
                if (response.isSuccessful) {
                    Log.d("Education", " Educacion creada correctamente: ${response.body()}")
                    isProfileSaved.value = true
                } else {
                    Log.e("ProfileViewModel", "Error: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("ProfileViewModelEducation", "Excepción: ${e.message}")
            }
        }
    }

    fun submitCertification(profile: CertificationRequest) {
        viewModelScope.launch {
            try {
                val response = useCase.submitCertification(profile)
                if (response.isSuccessful) {
                    Log.d("Certification", "Certificaciones creada correctamente: ${response.body()}")
                    isProfileSaved.value = true
                } else {
                    Log.e("ProfileViewModel", "Error: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("ProfileViewModelCertificate", "Excepción: ${e.message}")
            }
        }
    }
}