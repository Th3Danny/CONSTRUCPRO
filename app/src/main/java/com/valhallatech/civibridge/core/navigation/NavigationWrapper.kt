package com.valhallatech.civibridge.core.navigation

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.valhallatech.civibridge.core.network.RetrofitHelper.registerService
import com.valhallatech.civibridge.jobInformation.data.repository.InformationJobRepository
import com.valhallatech.civibridge.job.data.repository.JobRepository
import com.valhallatech.civibridge.notification.data.repository.NotificationRepository

import com.valhallatech.civibridge.project.data.repository.ProjectRepository
import com.valhallatech.civibridge.job.domain.GetAcceptedJobsUseCase
import com.valhallatech.civibridge.job.domain.GetJobsUseCase
import com.valhallatech.civibridge.jobInformation.domain.InformationJobUseCase
import com.valhallatech.civibridge.notification.domain.GetNotificationsUseCase
import com.valhallatech.civibridge.job.domain.GetPendingJobsUseCase

import com.valhallatech.civibridge.project.domain.GetProjectsUseCase
import com.valhallatech.civibridge.job.domain.PostJobsUseCase
import com.valhallatech.civibridge.jobInformation.presentation.InformationJobViewModel
import com.valhallatech.civibridge.jobInformation.presentation.InformationJobViewModelFactory
import com.valhallatech.civibridge.core.data.local.AppDatabase

import com.valhallatech.civibridge.job.presentation.JobScreen
import com.valhallatech.civibridge.job.presentation.JobViewModel
import com.valhallatech.civibridge.job.presentation.JobViewModelFactory
import com.valhallatech.civibridge.jobInformation.presentation.JobInformationScreen
import com.valhallatech.civibridge.notification.presentation.NotificationScreen
import com.valhallatech.civibridge.notification.presentation.NotificationViewModel
import com.valhallatech.civibridge.notification.presentation.NotificationViewModelFactory

import com.valhallatech.civibridge.project.presentation.ProjectScreen
import com.valhallatech.civibridge.login.presentation.LoginScreen
import com.valhallatech.civibridge.register.presentation.RegisterScreen
import com.valhallatech.civibridge.register.presentation.RegisterViewModel
import com.valhallatech.civibridge.register.presentation.RegisterViewModelFactory
import com.valhallatech.civibridge.login.data.repository.AuthRepository
import com.valhallatech.civibridge.login.domain.LoginUseCase
import com.valhallatech.civibridge.login.presentation.LoginViewModel
import com.valhallatech.civibridge.login.presentation.LoginViewModelFactory
import com.valhallatech.civibridge.profile.data.repository.ProfileRepository
import com.valhallatech.civibridge.profile.domain.ProfileUseCase
import com.valhallatech.civibridge.profile.presentation.ProfileConfigScreen
import com.valhallatech.civibridge.profile.presentation.ProfileScreen
import com.valhallatech.civibridge.profile.presentation.ProfileViewModel
import com.valhallatech.civibridge.profile.presentation.ProfileViewModelFactory
import com.valhallatech.civibridge.register.domain.RegisterUseCase
import com.valhallatech.civibridge.register.data.repository.RegisterRepository



@SuppressLint("RestrictedApi")
@Composable
fun NavigationWrapper() {
    val navController = rememberNavController()

    //  Crear instancias de los repositorios
    val loginRepository = AuthRepository
    val registerRepository = RegisterRepository(registerService)
    val chatRepository = InformationJobRepository()
    val projectRepository = ProjectRepository()
    val context = LocalContext.current
    val notificationRepository = NotificationRepository(context)


    //  Crear instancias de los UseCase con los repositorios correctos
    val loginUseCase = LoginUseCase(loginRepository)
    val registerUseCase = RegisterUseCase(registerRepository)
    val chatUseCase = InformationJobUseCase(chatRepository)
    val projectUseCase = GetProjectsUseCase(projectRepository)
    val notificationUseCase = GetNotificationsUseCase(notificationRepository)

    //Uso repetidos
    val database = AppDatabase.getDatabase(context)
    val jobRepository = JobRepository(context, database.pendingJobApplicationDao())
    val getJobsUseCase = GetJobsUseCase(jobRepository)
    val postJobsUseCase = PostJobsUseCase(jobRepository)
    val getPendingJobsUseCase = GetPendingJobsUseCase(jobRepository)
    val getAcceptedJobsUseCase = GetAcceptedJobsUseCase(jobRepository)
    val jobViewModel: JobViewModel = viewModel(
        factory = JobViewModelFactory(context, getJobsUseCase, getPendingJobsUseCase, getAcceptedJobsUseCase, postJobsUseCase)
    )

    val loginViewModel: LoginViewModel = viewModel(
        factory = LoginViewModelFactory(loginUseCase, context)
    )

    val sharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
    val target = sharedPreferences.getString("navigateTo", null)
    val startDestination = when {
        target == "JobInformationScreen" -> "JobInfo"
        target == "ProfileScreen" -> "ProfileScreen"
        target == "Notifications" -> "Notifications"
        sharedPreferences.getBoolean("isLoggedIn", false) -> "Home"
        else -> "Login"
    }


    if (startDestination != null) {


    NavHost(navController = navController, startDestination = startDestination) {

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


            JobScreen(
                navController = navController,
                jobViewModel = jobViewModel,
                loginViewModel = loginViewModel
            )
        }

        //  Pantalla de Informacion del trabajo
        composable("JobInfo") {
            val informationJob: InformationJobViewModel = viewModel(
                factory = InformationJobViewModelFactory(chatUseCase)
            )
            JobInformationScreen(
                navController = navController,
                viewModel = informationJob
            )
        }



        //  Pantalla de Proyectos
        composable("Projects") {
            ProjectScreen(
                navController = navController,
                jobViewModel = jobViewModel,
                loginViewModel = loginViewModel
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


        composable("ProfileConfig") {
            val profileViewModel: ProfileViewModel = viewModel(
                factory = ProfileViewModelFactory(ProfileUseCase(ProfileRepository()))
            )
            ProfileConfigScreen(
                navController = navController,
                viewModel = profileViewModel
            )
        }

        composable("ProfileScreen") {
            val context = LocalContext.current
            val profileFactory = ProfileViewModelFactory(ProfileUseCase(ProfileRepository()))

            val loginFactory = LoginViewModelFactory(LoginUseCase(loginRepository), context)

            val profileViewModel: ProfileViewModel = viewModel(factory = profileFactory)
            val loginViewModel: LoginViewModel = viewModel(factory = loginFactory)

            ProfileScreen(
                viewModel = profileViewModel,
                navController = navController,
                loginViewModel = loginViewModel
            )
        }


    }

    } else {
        // Mientras se decide a dónde ir, puedes mostrar un loader temporal
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }

}
