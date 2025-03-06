package com.example.myapplication.register.presentation

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.google.firebase.messaging.FirebaseMessaging

@Composable
fun RegisterScreen(
    registerViewModel: RegisterViewModel = viewModel(),
    navController: NavController,
    onNavigateToLogin: () -> Unit
) {
    val username by registerViewModel.username.observeAsState("")
    val name by registerViewModel.name.observeAsState("")
    val email by registerViewModel.email.observeAsState("")
    val password by registerViewModel.password.observeAsState("")
    val error by registerViewModel.error.observeAsState("")
    val success by registerViewModel.success.observeAsState(false)

    var fcmToken by remember { mutableStateOf("") }
    LaunchedEffect(Unit) {
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                fcmToken = task.result ?: ""
                Log.d("FCM", "Token obtenido para registro: $fcmToken")
            } else {
                Log.e("FCM", "Error obteniendo token FCM", task.exception)
            }
        }
    }

    LaunchedEffect(success) {
        if (success) {
            onNavigateToLogin()
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Construc",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Text(
                text = "Pro",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFFF9800)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .padding(16.dp),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.Black)
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = { onNavigateToLogin() },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = Color.Black
                        ),
                        shape = RoundedCornerShape(50)
                    ) {
                        Text("Inicio")
                    }

                    Button(
                        onClick = { /* Estamos en Registro */ },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFFF9800),
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(50)
                    ) {
                        Text("Registro")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text("Nombre de usuario", color = Color.White)
                TextField(
                    value = username,
                    onValueChange = { registerViewModel.onChangeUsername(it) },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.White,
                        unfocusedIndicatorColor = Color.Gray,
                        cursorColor = Color.White
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text("Nombre completo", color = Color.White)
                TextField(
                    value = name,
                    onValueChange = { registerViewModel.onChangeName(it) },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.White,
                        unfocusedIndicatorColor = Color.Gray,
                        cursorColor = Color.White
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text("Correo", color = Color.White)
                TextField(
                    value = email,
                    onValueChange = { registerViewModel.onChangeEmail(it) },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.White,
                        unfocusedIndicatorColor = Color.Gray,
                        cursorColor = Color.White
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text("Contraseña", color = Color.White)
                TextField(
                    value = password,
                    onValueChange = { registerViewModel.onChangePassword(it) },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.White,
                        unfocusedIndicatorColor = Color.Gray,
                        cursorColor = Color.White
                    ),
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = { registerViewModel.onRegister(fcmToken) },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF9800)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Registrarse", color = Color.White)
                }

                Spacer(modifier = Modifier.height(10.dp))

                if (error.isNotEmpty()) {
                    Text(text = error, color = Color.Red)
                }

                Text(
                    text = "¿Ya tienes cuenta? Inicia sesión",
                    color = Color.White,
                    fontSize = 12.sp,
                    modifier = Modifier.clickable { onNavigateToLogin() }
                )
            }
        }
    }
}

