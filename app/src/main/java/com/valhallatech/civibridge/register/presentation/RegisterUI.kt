package com.valhallatech.civibridge.register.presentation

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.valhallatech.civibridge.components.loginNavigate.SwitchButtons
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

    var passwordVisible by remember { mutableStateOf(false) }

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

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Logo de la app con más espacio
            Spacer(modifier = Modifier.height(40.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Civi",
                    fontSize = 42.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.surfaceContainer
                )
                Text(
                    text = "Bridge",
                    fontSize = 42.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.height(30.dp))

            // Tarjeta de registro más espaciosa
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 6.dp
                )
            ) {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 24.dp, vertical = 32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Switch de navegación
                    SwitchButtons(selectedTab = "Registro") { selected ->
                        if (selected == "Inicio") {
                            onNavigateToLogin()
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Campo de nombre de usuario con más espacio
                    OutlinedTextField(
                        value = username,
                        onValueChange = { registerViewModel.onChangeUsername(it) },
                        label = null,
                        placeholder = {
                            Text(
                                "Nombre de usuario",
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                            cursorColor = MaterialTheme.colorScheme.primary,
                            focusedContainerColor = MaterialTheme.colorScheme.surface,
                            unfocusedContainerColor = MaterialTheme.colorScheme.surface
                        ),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Next
                        ),
                        leadingIcon = {
                            Icon(
                                Icons.Default.Person,
                                contentDescription = "Usuario",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    )

                    // Campo de nombre completo con más espacio
                    OutlinedTextField(
                        value = name,
                        onValueChange = { registerViewModel.onChangeName(it) },
                        label = null,
                        placeholder = {
                            Text(
                                "Nombre completo",
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                            cursorColor = MaterialTheme.colorScheme.primary,
                            focusedContainerColor = MaterialTheme.colorScheme.surface,
                            unfocusedContainerColor = MaterialTheme.colorScheme.surface
                        ),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Next
                        ),
                        leadingIcon = {
                            Icon(
                                Icons.Default.Badge,
                                contentDescription = "Nombre",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    )

                    // Campo de correo electrónico con más espacio
                    OutlinedTextField(
                        value = email,
                        onValueChange = { registerViewModel.onChangeEmail(it) },
                        label = null,
                        placeholder = {
                            Text(
                                "Correo electrónico",
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                            cursorColor = MaterialTheme.colorScheme.primary,
                            focusedContainerColor = MaterialTheme.colorScheme.surface,
                            unfocusedContainerColor = MaterialTheme.colorScheme.surface
                        ),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email,
                            imeAction = ImeAction.Next
                        ),
                        leadingIcon = {
                            Icon(
                                Icons.Default.Email,
                                contentDescription = "Correo",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    )

                    // Campo de contraseña con más espacio
                    OutlinedTextField(
                        value = password,
                        onValueChange = { registerViewModel.onChangePassword(it) },
                        label = null,
                        placeholder = {
                            Text(
                                "Contraseña",
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                            cursorColor = MaterialTheme.colorScheme.primary,
                            focusedContainerColor = MaterialTheme.colorScheme.surface,
                            unfocusedContainerColor = MaterialTheme.colorScheme.surface
                        ),
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Done
                        ),
                        singleLine = true,
                        leadingIcon = {
                            Icon(
                                Icons.Default.Lock,
                                contentDescription = "Contraseña",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        },
                        trailingIcon = {
                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(
                                    if (passwordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                    contentDescription = if (passwordVisible) "Ocultar contraseña" else "Mostrar contraseña",
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    )

                    // Mensaje de error si existe
                    AnimatedVisibility(visible = error.isNotEmpty()) {
                        Text(
                            text = error,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Botón de registro más grande
                    Button(
                        onClick = { registerViewModel.onRegister(fcmToken) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(12.dp),
                        elevation = ButtonDefaults.buttonElevation(
                            defaultElevation = 4.dp
                        )
                    ) {
                        Text(
                            "Registrarse",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Enlace para ir a inicio de sesión
                    Row(
                        modifier = Modifier.clickable { onNavigateToLogin() },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "¿Ya tienes cuenta? ",
                            color = MaterialTheme.colorScheme.onSurface,
                            fontSize = 14.sp
                        )
                        Text(
                            text = "Inicia sesión",
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}
