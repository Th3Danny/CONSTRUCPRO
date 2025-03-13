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

    private val _username = MutableLiveData("")
    val username: LiveData<String> = _username

    private val _name = MutableLiveData("")
    val name: LiveData<String> = _name

    private val _email = MutableLiveData("")
    val email: LiveData<String> = _email

    private val _password = MutableLiveData("")
    val password: LiveData<String> = _password

    private val _success = MutableLiveData(false)
    val success: LiveData<Boolean> = _success

    private val _error = MutableLiveData("")
    val error: LiveData<String> = _error

    fun onChangeUsername(username: String) {
        Log.d("RegisterViewModel", " Usuario cambiado: $username")
        _username.value = username
    }

    fun onChangeName(name: String) {
        Log.d("RegisterViewModel", " Nombre cambiado: $name")
        _name.value = name
    }

    fun onChangeEmail(email: String) {
        Log.d("RegisterViewModel", " Correo cambiado: $email")
        _email.value = email
    }

    fun onChangePassword(password: String) {
        Log.d("RegisterViewModel", " Contraseña cambiada")
        _password.value = password
    }

    fun onRegister(fcmToken: String) {
        viewModelScope.launch {
            Log.d("RegisterViewModel", "📡 Intentando registrar usuario: ${_email.value}")

            val request = RegisterRequest(
                username = _username.value ?: "",
                name = _name.value ?: "",
                email = _email.value ?: "",
                password = _password.value ?: "",
                fcm = fcmToken,
                roles = listOf("USER")
            )

            Log.d("RegisterViewModel", " Payload de Registro: $request")

            val result = registerUseCase(request)
            result.onSuccess {
                Log.d("RegisterViewModel", " Registro exitoso para ${request.email}")
                _success.value = true
                _error.value = ""
            }.onFailure { exception ->
                Log.e("RegisterViewModel", " Error en el registro: ${exception.message}")
                _success.value = false
                _error.value = exception.message ?: "Error desconocido"
            }
        }
    }
}

