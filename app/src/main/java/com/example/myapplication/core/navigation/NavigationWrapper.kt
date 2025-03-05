package com.example.myapplication.core.navigation

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.home.presentation.ChatScreen
import com.example.myapplication.home.presentation.HomeScreen
import com.example.myapplication.home.presentation.NotificationScreen
import com.example.myapplication.home.presentation.ProjectScreen
import com.example.myapplication.login.presentation.LoginScreen
import com.example.myapplication.login.presentation.LoginViewModel
import com.example.myapplication.login.presentation.LoginViewModelFactory
import com.example.myapplication.register.presentation.RegisterScreen
import com.example.myapplication.register.presentation.RegisterViewModel
import com.example.myapplication.register.presentation.RegisterViewModelFactory
import com.example.myapplication.login.domain.LoginUseCase
import com.example.myapplication.login.data.repository.AuthRepository
import com.example.myapplication.register.domain.RegisterUseCase
import com.example.myapplication.register.data.repository.RegisterRepository

@SuppressLint("RestrictedApi")
@Composable
fun NavigationWrapper() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "Login") {
        // 🔹 Pantalla de Inicio de Sesión
        composable("Login") {
            val loginViewModel: LoginViewModel = viewModel(
                factory = LoginViewModelFactory(LoginUseCase(AuthRepository()))
            )

            LoginScreen(
                loginViewModel = loginViewModel,
                navController = navController,
                onNavigateToRegister = { navController.navigate("Register") },
                onNavigateToHome = { navController.navigate("Home") }
            )
        }

        // 🔹 Pantalla de Registro
        composable("Register") {
            val registerViewModel: RegisterViewModel = viewModel(
                factory = RegisterViewModelFactory(RegisterUseCase(RegisterRepository()))
            )

            RegisterScreen(
                registerViewModel = registerViewModel,
                navController = navController,
                onNavigateToLogin = { navController.popBackStack() }
            )
        }

        // 🔹 Pantalla de Home (Publicaciones)
        composable("Home") { HomeScreen(navController) }

        // 🔹 Pantalla de Chat
        composable("Chat") { ChatScreen(navController) }

        // 🔹 Pantalla de Proyectos
        composable("Projects") { ProjectScreen(navController) }

        // 🔹 Pantalla de Notificaciones
        composable("Notifications") { NotificationScreen(navController) }
    }
}
