package com.example.myapplication.register.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.register.data.model.RegisterRequest
import com.example.myapplication.register.domain.RegisterUseCase
import kotlinx.coroutines.launch

class RegisterViewModel(private val registerUseCase: RegisterUseCase) : ViewModel() {

    private val _user = MutableLiveData("")
    val user: LiveData<String> = _user

    private val _email = MutableLiveData("")
    val email: LiveData<String> = _email

    private val _password = MutableLiveData("")
    val password: LiveData<String> = _password

    private val _success = MutableLiveData(false)
    val success: LiveData<Boolean> = _success

    private val _error = MutableLiveData("")
    val error: LiveData<String> = _error

    fun onChangeUser(user: String) {
        _user.value = user
    }

    fun onChangeEmail(email: String) {
        _email.value = email
    }

    fun onChangePassword(password: String) {
        _password.value = password
    }

    fun onRegister() {
        viewModelScope.launch {
            Log.d("RegisterViewModel", "Intentando registrar usuario: ${_email.value}")

            val request = RegisterRequest(
                user = _user.value ?: "",
                email = _email.value ?: "",
                password = _password.value ?: ""
            )

            val result = registerUseCase(request)
            result.onSuccess {
                _success.value = true
                _error.value = ""
            }.onFailure { exception ->
                _success.value = false
                _error.value = exception.message ?: "Error desconocido"
            }
        }
    }
}
