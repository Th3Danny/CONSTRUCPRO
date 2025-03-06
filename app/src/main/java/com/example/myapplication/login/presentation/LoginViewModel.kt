package com.example.myapplication.login.presentation

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.login.data.model.LoginRequest
import com.example.myapplication.login.domain.LoginUseCase
import kotlinx.coroutines.launch

class LoginViewModel(private val loginUseCase: LoginUseCase) : ViewModel() {

    private val _username = MutableLiveData<String>("") // Inicializado
    val username: LiveData<String> = _username

    private val _password = MutableLiveData<String>("") // Inicializado
    val password: LiveData<String> = _password

    private val _success = MutableLiveData(false)
    val success: LiveData<Boolean> = _success

    private val _error = MutableLiveData("")
    val error: LiveData<String> = _error

    private val _token = MutableLiveData<String?>()
    val token: LiveData<String?> = _token

    fun onLogin(email: String, password: String) {
        viewModelScope.launch {
            Log.d("LoginViewModel", "onLogin iniciado con usuario: $email")


            try {
                val loginRequest = LoginRequest(
                   email = email,  // Usar el parámetro recibido
                   password = password
)

               val result = loginUseCase(loginRequest)
                result.onSuccess { loginResponse ->
                   Log.d("LoginViewModel", "Login exitoso: Token recibido")
                   _success.value = true
                   _error.value = ""
                  _token.value = loginResponse.token
                }.onFailure { exception ->
                    Log.e("LoginViewModel", "Login fallido: ${exception.message}")
                   _success.value = false
                   _error.value = exception.message ?: "Error desconocido"
               }
          } catch (e: Exception) {
                Log.e("LoginViewModel", "Excepción: ${e.message}")
                _success.value = false
                _error.value = e.message ?: "Error al intentar realizar la operación"
            }
        }
    }



    fun onChangeUsername(username: String) {
        _username.value = username
    }

    fun onChangePassword(password: String) {
        _password.value = password
    }
}
