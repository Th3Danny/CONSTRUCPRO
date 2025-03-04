package com.example.myapplication.core.navigation

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.home.data.repository.ChatRepository
import com.example.myapplication.home.data.repository.PostRepository
import com.example.myapplication.home.data.repository.ProjectRepository
import com.example.myapplication.home.domain.GetMessagesUseCase
import com.example.myapplication.home.domain.GetPostsUseCase
import com.example.myapplication.home.domain.GetProjectsUseCase
import com.example.myapplication.home.presentation.ChatScreen
import com.example.myapplication.home.presentation.HomeScreen
import com.example.myapplication.home.presentation.ProjectScreen
import com.example.myapplication.home.presentation.ChatViewModel
import com.example.myapplication.home.presentation.ChatViewModelFactory
import com.example.myapplication.home.presentation.PostViewModel
import com.example.myapplication.home.presentation.PostViewModelFactory
import com.example.myapplication.home.presentation.ProjectViewModel
import com.example.myapplication.home.presentation.ProjectViewModelFactory
import com.example.myapplication.login.data.repository.AuthRepository
import com.example.myapplication.login.domain.LoginUseCase
import com.example.myapplication.login.presentation.LoginScreen
import com.example.myapplication.login.presentation.LoginViewModel
import com.example.myapplication.login.presentation.LoginViewModelFactory
import com.example.myapplication.register.data.repository.RegisterRepository
import com.example.myapplication.register.domain.RegisterUseCase
import com.example.myapplication.register.presentation.RegisterScreen
import com.example.myapplication.register.presentation.RegisterViewModel
import com.example.myapplication.register.presentation.RegisterViewModelFactory

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@SuppressLint("RestrictedApi")
@Composable
fun NavigationWrapper() {
    val navController = rememberNavController()

    // Repositorios y Casos de Uso
    val loginRepository = AuthRepository()
    val loginUseCase = LoginUseCase(loginRepository)

    val registerRepository = RegisterRepository()
    val registerUseCase = RegisterUseCase(registerRepository)


    val postRepository = PostRepository()
    val postUseCase = GetPostsUseCase(postRepository)

    val chatRepository = ChatRepository()
    val chatUseCase = GetMessagesUseCase(chatRepository)

    val projectRepository = ProjectRepository()
    val projectUseCase = GetProjectsUseCase(projectRepository)

    NavHost(navController = navController, startDestination = "Login") {
        // 🔹 Pantalla de Inicio de Sesión
        composable("Login") {
            val loginViewModel: LoginViewModel = viewModel(
                factory = LoginViewModelFactory(loginUseCase)
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

        composable("Home") {

            val selectedTab = remember { mutableStateOf("Publicaciones") }

            val postViewModel: PostViewModel = viewModel(
                factory = PostViewModelFactory(postUseCase)
            )

            HomeScreen(
                selectedTab = selectedTab.value,
                onTabSelected = { selectedTab.value = it },
                postViewModel = postViewModel,
                navController = navController,
                onNavigateToChat = { navController.navigate("Chat") },
                onNavigateToProjects = { navController.navigate("Projects") }
            )
        }



        // 🔹 Pantalla de Chat
        composable("Chat") {
            val chatViewModel: ChatViewModel = viewModel(
                factory = ChatViewModelFactory(chatUseCase)
            )

            ChatScreen(
                chatViewModel = chatViewModel,
                userId = "user123"  // ID de prueba
            )
        }

        // 🔹 Pantalla de Proyectos
        composable("Projects") {
            val projectViewModel: ProjectViewModel = viewModel(
                factory = ProjectViewModelFactory(projectUseCase)
            )

            ProjectScreen(
                projectViewModel = projectViewModel
            )
        }
    }
}
