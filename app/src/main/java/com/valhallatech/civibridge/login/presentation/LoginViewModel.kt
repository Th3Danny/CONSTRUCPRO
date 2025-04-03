package com.valhallatech.civibridge.login.presentation

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.valhallatech.civibridge.login.data.model.LoginRequest
import com.valhallatech.civibridge.login.domain.LoginUseCase
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.launch
import android.widget.Toast

class LoginViewModel(
    private val loginUseCase: LoginUseCase,
    private val context: Context
) : ViewModel() {

    private val _username = MutableLiveData<String>("")
    val username: LiveData<String> = _username

    private val _password = MutableLiveData<String>("")
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

            // 🔹 Obtener el token de FCM antes de la petición de login
            FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val fcmToken = task.result
                    Log.d("LoginViewModel", "FCM token obtenido: $fcmToken")

                    // 🔹 Continuar con el login
                    viewModelScope.launch {
                        try {
                            val loginRequest = LoginRequest(
                                email = email,
                                password = password,
                                fcm_token = fcmToken
                            )

                            val result = loginUseCase(loginRequest)
                            result.onSuccess { loginResponse ->
                                Log.d("LoginViewModel", "Login exitoso")
                                _success.value = true
                                _error.value = ""
                                _token.value = loginResponse.data.token

                                // Guardar user y profile
                                saveUserId(loginResponse.data.idUser)
                                saveProfileId(loginResponse.data.idProfile)

                                val sharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
                                with(sharedPreferences.edit()) {
                                    putString("authToken", loginResponse.data.token)
                                    apply()
                                }

                            }.onFailure { exception ->
                                Log.e("LoginViewModel", "Login fallido: ${exception.message}")
                                _success.value = false
                                _error.value = exception.message ?: "Error desconocido"
                            }
                        } catch (e: Exception) {
                            Log.e("LoginViewModel", "Excepción: ${e.message}")
                            _success.value = false
                            _error.value = e.message ?: "Error de red"
                        }
                    }

                } else {
                    Log.e("LoginViewModel", "No se pudo obtener el token FCM")
                    _error.value = "Error al obtener token FCM"
                }
            }
        }
    }


    private fun saveUserId(userId: Int) {
        val sharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putInt("userId", userId)
            apply()
        }
        Log.d("LoginViewModel", " userId guardado en SharedPreferences: $userId")
    }

    private fun saveProfileId(idProfile: Int) {
        val sharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putInt("idProfile", idProfile)
            apply()
        }
        Log.d("LoginViewModel", " idProfile guardado en SharedPreferences: $idProfile")
    }

    fun saveSession(context: Context) {
        val sharedPref = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putBoolean("isLoggedIn", true)
            apply()
        }
    }

    internal fun logout(context: Context) {
        val prefs = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        prefs.edit().clear().apply()
        Toast.makeText(context, "Sesión cerrada", Toast.LENGTH_SHORT).show()
    }


    fun onChangeUsername(username: String) {
        _username.value = username
    }

    fun onChangePassword(password: String) {
        _password.value = password
    }
}
