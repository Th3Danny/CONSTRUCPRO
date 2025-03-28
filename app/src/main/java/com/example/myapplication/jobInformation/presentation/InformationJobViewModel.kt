package com.example.myapplication.jobInformation.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.jobInformation.domain.InformationJobUseCase
import com.example.myapplication.jobInformation.data.model.InformationJobRequest

class InformationJobViewModel(private val getInformationUseCase: InformationJobUseCase) : ViewModel() {

    private val _info = MutableLiveData<InformationJobRequest?>()
    val info: LiveData<InformationJobRequest?> = _info

    // Estado de carga
    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> = _isLoading

    // Estado de error
    private val _error = MutableLiveData<String>("")
    val error: LiveData<String> = _error

    fun fetchInformation(jobId: String) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _error.value = ""

                val result = getInformationUseCase(jobId)
                _info.value = result.getOrNull()

                if (result.isFailure) {
                    _error.value = result.exceptionOrNull()?.message ?: "Error desconocido"
                }

                _isLoading.value = false
            } catch (e: Exception) {
                _error.value = e.message ?: "Error al obtener la información del trabajo"
                _isLoading.value = false
            }
        }
    }
    fun clearData() {
        _info.value = null
        _error.value = ""
    }
}
