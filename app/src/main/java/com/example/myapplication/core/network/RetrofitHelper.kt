package com.example.myapplication.core.network



import com.example.myapplication.chat.data.datasource.ChatService
import com.example.myapplication.job.data.datasource.JobService
import com.example.myapplication.job.data.datasource.JobsService
import com.example.myapplication.notification.data.datasource.NotificationService
import com.example.myapplication.project.data.datasource.ProjectService
import com.example.myapplication.login.data.datasource.LoginService
import com.example.myapplication.register.data.datasource.RegisterService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {
    private const val BASE_URL = "http://4.tcp.ngrok.io:18718/api/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    val loginService: LoginService = retrofit.create(LoginService::class.java)
    val registerService: RegisterService = retrofit.create(RegisterService::class.java)
    val chatService: ChatService = retrofit.create(ChatService::class.java)
    val jobService: JobsService = retrofit.create(JobsService::class.java)
    val JobServicePost : JobService = retrofit.create(JobService::class.java)
    val projectService: ProjectService = retrofit.create(ProjectService::class.java)
    val notificationService: NotificationService = retrofit.create(NotificationService::class.java)
}