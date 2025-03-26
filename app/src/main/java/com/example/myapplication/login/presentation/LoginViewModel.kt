package com.example.myapplication.login.presentation

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.core.notification.FirebaseHelper
import com.example.myapplication.login.data.model.LoginRequest
import com.example.myapplication.login.domain.LoginUseCase
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

            try {
                val loginRequest = LoginRequest(
                    email = email,
                    password = password
                )

                val result = loginUseCase(loginRequest)
                result.onSuccess { loginResponse ->
                    Log.d("LoginViewModel", " Login exitoso, Token recibido")
                    Log.d("LoginViewModel", " userId en respuesta: ${loginResponse.data.idUser}")

                    _success.value = true
                    _error.value = ""
                    _token.value = loginResponse.data.token

                    //  Guardamos el userId después del login
                    saveUserId(loginResponse.data.idUser)

                    //  Guardar token en SharedPreferences
                    val sharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
                    with(sharedPreferences.edit()) {
                        putString("authToken", loginResponse.data.token)
                        apply()
                    }

                    //  Ahora obtenemos el token FCM y lo enviamos al backend
                    sendFcmTokenToBackend()
                }
                    .onFailure { exception ->
                    Log.e("LoginViewModel", " Login fallido: ${exception.message}")
                    _success.value = false
                    _error.value = exception.message ?: "Error desconocido"
                }
            } catch (e: Exception) {
                Log.e("LoginViewModel", " Excepción en el login: ${e.message}")
                _success.value = false
                _error.value = e.message ?: "Error al intentar realizar la operación"
            }
        }
    }

    //  Obtener el token FCM y enviarlo al backend
    private fun sendFcmTokenToBackend() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val fcmToken = task.result
                Log.d("FCM", " Token FCM en login: $fcmToken")

                val sharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
                val authToken = sharedPreferences.getString("authToken", "")

                if (!authToken.isNullOrEmpty()) {
                    FirebaseHelper.sendTokenToServer(context, fcmToken)
                } else {
                    Log.e("FCM", " No se enviará el token de FCM porque el usuario no ha iniciado sesión.")
                }
            } else {
                Log.w("FCM", "Error al obtener token de FCM en login", task.exception)
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
