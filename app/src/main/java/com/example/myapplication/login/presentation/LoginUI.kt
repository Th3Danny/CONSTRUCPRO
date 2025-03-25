package com.example.myapplication.login.presentation


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.runtime.livedata.observeAsState
import com.example.myapplication.components.loginNavigate.SwitchButtons


@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel,
    navController: NavController,
    onNavigateToRegister: () -> Unit,
    onNavigateToHome: () -> Unit
) {
    val email by loginViewModel.username.observeAsState("")
    val password by loginViewModel.password.observeAsState("")
    val error by loginViewModel.error.observeAsState("")
    val success by loginViewModel.success.observeAsState(false)

    LaunchedEffect(success) {
        if (success) {
            onNavigateToHome()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(20.dp),
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
            // Imagen del logo
//            Image(
//                painter = painterResource(id = R.drawable.logo),
//                contentDescription = "Logo de la app",
//                modifier = Modifier
//                    .height(500.dp)
//                    .padding(bottom = 150.dp)
//            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth(0.9f),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onPrimaryContainer)
        ) {

            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                SwitchButtons(selectedTab = "Inicio") { selected ->
                    if (selected == "Registro") {
                        onNavigateToRegister()
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                //Text("Correo", color = MaterialTheme.colorScheme.onBackground)
                TextField(
                    value = email,
                    onValueChange = { loginViewModel.onChangeUsername(it) },
                    label = { Text("Correo") },
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = LocalTextStyle.current.copy(color = MaterialTheme.colorScheme.onBackground),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        focusedIndicatorColor = MaterialTheme.colorScheme.onBackground,
                        unfocusedIndicatorColor = Color.Gray,
                        cursorColor = MaterialTheme.colorScheme.onBackground
                    )
                )

                Spacer(modifier = Modifier.height(10.dp))

                //Text("Contraseña", color = MaterialTheme.colorScheme.onBackground)
                TextField(
                    value = password,
                    onValueChange = { loginViewModel.onChangePassword(it) },
                    label = { Text("Contraseña") },
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = LocalTextStyle.current.copy(color = MaterialTheme.colorScheme.onBackground),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        focusedIndicatorColor = MaterialTheme.colorScheme.onBackground,
                        unfocusedIndicatorColor = Color.Gray,
                        cursorColor = MaterialTheme.colorScheme.onBackground
                    ),
                    visualTransformation = PasswordVisualTransformation()
                )

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = { loginViewModel.onLogin(email, password) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Iniciar Sesión", color = Color.White)
                }

                Spacer(modifier = Modifier.height(10.dp))

                if (error.isNotEmpty()) {
                    Text(text = error, color = Color.Red)
                }

                Text(
                    text = "¿Olvidaste tu contraseña?",
                    color = Color.White,
                    fontSize = 12.sp,
                    modifier = Modifier.clickable { /* Acción para recuperar contraseña */ }
                )
            }
        }
    }
}


