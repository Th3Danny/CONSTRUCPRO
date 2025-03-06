package com.example.myapplication.core.network



import com.example.myapplication.home.data.datasource.ChatService
import com.example.myapplication.home.data.datasource.NotificationService
import com.example.myapplication.home.data.datasource.PostService
import com.example.myapplication.home.data.datasource.ProjectService
import com.example.myapplication.login.data.datasource.LoginService
import com.example.myapplication.register.data.datasource.RegisterService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitHelper {
    private const val BASE_URL = "http://0.tcp.ngrok.io:17026/api/"


    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    val loginService: LoginService = retrofit.create(LoginService::class.java)
    val registerService: RegisterService = retrofit.create(RegisterService::class.java)
    val chatService: ChatService = retrofit.create(ChatService::class.java)
    val jobService: PostService = retrofit.create(PostService::class.java)
    val projectService: ProjectService = retrofit.create(ProjectService::class.java)
    val notificationService: NotificationService = retrofit.create(NotificationService::class.java)
}