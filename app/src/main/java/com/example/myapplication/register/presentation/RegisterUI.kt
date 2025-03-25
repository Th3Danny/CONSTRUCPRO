package com.example.myapplication.register.presentation

import android.util.Log
import androidx.compose.foundation.background
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
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.myapplication.components.loginNavigate.SwitchButtons
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
        modifier = Modifier.fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Civi",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.surface
            )
            Text(
                text = "Bridge",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .padding(16.dp),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onPrimaryContainer)
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                SwitchButtons(selectedTab = "Registro") { selected ->
                    if (selected == "Inicio") {
                        onNavigateToLogin()
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

               // Text("Nombre de usuario", color = MaterialTheme.colorScheme.onBackground)
                TextField(
                    value = username,
                    onValueChange = { registerViewModel.onChangeUsername(it) },
                    label = { Text("Nombre de usuario") },
                    textStyle = LocalTextStyle.current.copy(color = Color.White),
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

                //Text("Nombre completo", color = MaterialTheme.colorScheme.onBackground)
                TextField(
                    value = name,
                    onValueChange = { registerViewModel.onChangeName(it) },
                    label = {Text("Nombre completo")},
                    textStyle = LocalTextStyle.current.copy(color = Color.White),
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

                //Text("Correo", color = MaterialTheme.colorScheme.onBackground)
                TextField(
                    value = email,
                    onValueChange = { registerViewModel.onChangeEmail(it) },
                    label = {Text("Correo")},
                    textStyle = LocalTextStyle.current.copy(color = Color.White),
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

                //Text("Contraseña", color = MaterialTheme.colorScheme.onBackground)
                TextField(
                    value = password,
                    onValueChange = { registerViewModel.onChangePassword(it) },
                    label = {Text("Contraseña")},
                    textStyle = LocalTextStyle.current.copy(color = Color.White),
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
                    Text("Registrarse", color = MaterialTheme.colorScheme.onBackground)
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
