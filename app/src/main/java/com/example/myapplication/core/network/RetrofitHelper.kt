package com.example.myapplication.core.network



import com.example.myapplication.chat.data.datasource.ChatService
import com.example.myapplication.job.data.datasource.JobService
import com.example.myapplication.job.data.datasource.JobsService
import com.example.myapplication.notification.data.datasource.NotificationService
import com.example.myapplication.project.data.datasource.ProjectService
import com.example.myapplication.login.data.datasource.LoginService
import com.example.myapplication.profile.data.datasource.ProfileService
import com.example.myapplication.register.data.datasource.RegisterService
import com.example.myapplication.utils.LocalDateAdapter
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDate

object RetrofitHelper {
    private const val BASE_URL = "http://52.21.21.212:8080/api/"

    private val gson = GsonBuilder()
        .registerTypeAdapter(LocalDate::class.java, LocalDateAdapter())
        .create()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()


    val loginService: LoginService = retrofit.create(LoginService::class.java)
    val registerService: RegisterService = retrofit.create(RegisterService::class.java)
    val chatService: ChatService = retrofit.create(ChatService::class.java)
    val jobService: JobsService = retrofit.create(JobsService::class.java)
    val JobServicePost : JobService = retrofit.create(JobService::class.java)
    val projectService: ProjectService = retrofit.create(ProjectService::class.java)
    val notificationService: NotificationService = retrofit.create(NotificationService::class.java)
    val profileService : ProfileService = retrofit.create(ProfileService::class.java)
}