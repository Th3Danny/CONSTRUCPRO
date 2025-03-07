package com.example.myapplication.chat.data.model

data class Message(
    val id: String,
    val sender: String,
    val receiver: String,
    val content: String,
    val timestamp: String
)
