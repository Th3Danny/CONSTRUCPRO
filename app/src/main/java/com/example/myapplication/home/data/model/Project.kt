package com.example.myapplication.home.data.model

data class Project(
    val id: String,
    val name: String,
    val description: String,
    val author: String,  // ✅ Ahora está correctamente definido
    val deadline: String = "2024-12-31",
    val status: String = "En progreso"
)
