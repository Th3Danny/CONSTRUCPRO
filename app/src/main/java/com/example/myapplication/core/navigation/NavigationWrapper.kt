package com.example.myapplication.core.navigation

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.core.network.RetrofitHelper.registerService
import com.example.myapplication.chat.data.repository.ChatRepository
import com.example.myapplication.job.data.repository.JobRepository
import com.example.myapplication.notification.data.repository.NotificationRepository

import com.example.myapplication.project.data.repository.ProjectRepository
import com.example.myapplication.job.domain.GetAcceptedJobsUseCase
import com.example.myapplication.job.domain.GetJobsUseCase
import com.example.myapplication.chat.domain.GetMessagesUseCase
import com.example.myapplication.notification.domain.GetNotificationsUseCase
import com.example.myapplication.job.domain.GetPendingJobsUseCase

import com.example.myapplication.project.domain.GetProjectsUseCase
import com.example.myapplication.job.domain.PostJobsUseCase
import com.example.myapplication.chat.presentation.ChatScreen
import com.example.myapplication.chat.presentation.ChatViewModel
import com.example.myapplication.chat.presentation.ChatViewModelFactory

import com.example.myapplication.job.presentation.JobScreen
import com.example.myapplication.job.presentation.JobViewModel
import com.example.myapplication.job.presentation.JobViewModelFactory
import com.example.myapplication.notification.presentation.NotificationScreen
import com.example.myapplication.notification.presentation.NotificationViewModel
import com.example.myapplication.notification.presentation.NotificationViewModelFactory

import com.example.myapplication.project.presentation.ProjectScreen
import com.example.myapplication.project.presentation.ProjectViewModel
import com.example.myapplication.project.presentation.ProjectViewModelFactory
import com.example.myapplication.login.presentation.LoginScreen
import com.example.myapplication.register.presentation.RegisterScreen
import com.example.myapplication.register.presentation.RegisterViewModel
import com.example.myapplication.register.presentation.RegisterViewModelFactory
import com.example.myapplication.login.data.repository.AuthRepository
import com.example.myapplication.login.domain.LoginUseCase
import com.example.myapplication.login.presentation.LoginViewModel
import com.example.myapplication.login.presentation.LoginViewModelFactory
import com.example.myapplication.register.domain.RegisterUseCase
import com.example.myapplication.register.data.repository.RegisterRepository


@SuppressLint("RestrictedApi")
@Composable
fun NavigationWrapper() {
    val navController = rememberNavController()

    //  Crear instancias de los repositorios
    val loginRepository = AuthRepository()
    val registerRepository = RegisterRepository(registerService)
    val chatRepository = ChatRepository()
    val projectRepository = ProjectRepository()
    val context = LocalContext.current
    val notificationRepository = NotificationRepository(context)


    //  Crear instancias de los UseCase con los repositorios correctos
    val loginUseCase = LoginUseCase(loginRepository)
    val registerUseCase = RegisterUseCase(registerRepository)
    val chatUseCase = GetMessagesUseCase(chatRepository)
    val projectUseCase = GetProjectsUseCase(projectRepository)
    val notificationUseCase = GetNotificationsUseCase(notificationRepository)

    NavHost(navController = navController, startDestination = "Login") {

        //  Pantalla de Inicio de Sesión
        composable("Login") {
            val loginViewModel: LoginViewModel = viewModel(
                factory = LoginViewModelFactory(loginUseCase, LocalContext.current)
            )

            LoginScreen(
                loginViewModel = loginViewModel,
                navController = navController,
                onNavigateToRegister = { navController.navigate("Register") },
                onNavigateToHome = { navController.navigate("Home") }
            )
        }

        //  Pantalla de Registro
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

        //  Pantalla de Home (Publicaciones)
        composable("Home") {
            val jobRepository = JobRepository(LocalContext.current)
            val getJobsUseCase = GetJobsUseCase(jobRepository)
            val postJobsUseCase = PostJobsUseCase(jobRepository)
            val getPendingJobsUseCase = GetPendingJobsUseCase(jobRepository)
            val getAcceptedJobsUseCase = GetAcceptedJobsUseCase(jobRepository)

            val jobViewModel: JobViewModel = viewModel(
                factory = JobViewModelFactory(getJobsUseCase, getPendingJobsUseCase,getAcceptedJobsUseCase, postJobsUseCase) //)
            )

            JobScreen(
                navController = navController,
                jobViewModel = jobViewModel
            )
        }



        //  Pantalla de Chat
        composable("Chat") {
            val chatViewModel: ChatViewModel = viewModel(
                factory = ChatViewModelFactory(chatUseCase)
            )

            ChatScreen(
                chatViewModel = chatViewModel,
                navController = navController
            )
        }

        //  Pantalla de Proyectos
        composable("Projects") {
            val projectViewModel: ProjectViewModel = viewModel(
                factory = ProjectViewModelFactory(projectUseCase)
            )

            ProjectScreen(
                projectViewModel = projectViewModel,
                navController = navController
            )
        }

        //  Pantalla de Notificaciones
        composable("Notifications") {
            val notificationViewModel: NotificationViewModel = viewModel(
                factory = NotificationViewModelFactory(GetNotificationsUseCase(notificationRepository))
            )

            NotificationScreen(
                notificationViewModel = notificationViewModel,
                navController = navController
            )
        }

    }
}
