package com.example.myapplication.core.navigation

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.core.network.RetrofitHelper.registerService
import com.example.myapplication.home.data.repository.ChatRepository
import com.example.myapplication.home.data.repository.JobRepository
import com.example.myapplication.home.data.repository.NotificationRepository

import com.example.myapplication.home.data.repository.ProjectRepository
import com.example.myapplication.home.domain.GetJobsUseCase
import com.example.myapplication.home.domain.GetMessagesUseCase
import com.example.myapplication.home.domain.GetNotificationsUseCase

import com.example.myapplication.home.domain.GetProjectsUseCase
import com.example.myapplication.home.presentation.ChatScreen
import com.example.myapplication.home.presentation.ChatViewModel
import com.example.myapplication.home.presentation.ChatViewModelFactory
import com.example.myapplication.home.presentation.HomeScreen

import com.example.myapplication.home.presentation.JobScreen
import com.example.myapplication.home.presentation.JobViewModel
import com.example.myapplication.home.presentation.JobViewModelFactory
import com.example.myapplication.home.presentation.NotificationScreen
import com.example.myapplication.home.presentation.NotificationViewModel
import com.example.myapplication.home.presentation.NotificationViewModelFactory

import com.example.myapplication.home.presentation.ProjectScreen
import com.example.myapplication.home.presentation.ProjectViewModel
import com.example.myapplication.home.presentation.ProjectViewModelFactory
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

    // ✅ Crear instancias de los repositorios
    val loginRepository = AuthRepository()
    val registerRepository = RegisterRepository(registerService)
    val chatRepository = ChatRepository()
    val projectRepository = ProjectRepository()
    val context = LocalContext.current
    val notificationRepository = NotificationRepository(context)


    // ✅ Crear instancias de los UseCase con los repositorios correctos
    val loginUseCase = LoginUseCase(loginRepository)
    val registerUseCase = RegisterUseCase(registerRepository)
    val chatUseCase = GetMessagesUseCase(chatRepository)
    val projectUseCase = GetProjectsUseCase(projectRepository)
    val notificationUseCase = GetNotificationsUseCase(notificationRepository)

    NavHost(navController = navController, startDestination = "Login") {

        // 🔹 Pantalla de Inicio de Sesión
        composable("Login") {
            val loginViewModel: LoginViewModel = viewModel(
                factory = LoginViewModelFactory(loginUseCase, LocalContext.current) // ✅ Ahora se pasa el contexto
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
                factory = RegisterViewModelFactory(registerUseCase)
            )

            RegisterScreen(
                registerViewModel = registerViewModel,
                navController = navController,
                onNavigateToLogin = { navController.popBackStack() }
            )
        }

        // 🔹 Pantalla de Home (Publicaciones)
        composable("Home") {
            val jobViewModel: JobViewModel = viewModel(
                factory = JobViewModelFactory(GetJobsUseCase(JobRepository()))
            )

            HomeScreen(
                navController = navController,
                jobViewModel = jobViewModel
            )
        }


        // 🔹 Pantalla de Chat
        composable("Chat") {
            val chatViewModel: ChatViewModel = viewModel(
                factory = ChatViewModelFactory(chatUseCase)
            )

            ChatScreen(
                chatViewModel = chatViewModel,
                navController = navController
            )
        }

        // 🔹 Pantalla de Proyectos
        composable("Projects") {
            val projectViewModel: ProjectViewModel = viewModel(
                factory = ProjectViewModelFactory(projectUseCase)
            )

            ProjectScreen(
                projectViewModel = projectViewModel,
                navController = navController
            )
        }

        // 🔹 Pantalla de Notificaciones
        composable("Notifications") {
            val notificationViewModel: NotificationViewModel = viewModel(
                factory = NotificationViewModelFactory(GetNotificationsUseCase(notificationRepository)) // ✅ Se usa el repo con contexto
            )

            NotificationScreen(
                notificationViewModel = notificationViewModel,
                navController = navController
            )
        }

    }
}
