package com.example.myapplication.chat.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.myapplication.core.navigation.BottomNavigationBar
import com.example.myapplication.chat.data.model.Message

@Composable
fun ChatScreen(navController: NavController, chatViewModel: ChatViewModel = viewModel()) {
    val messages by chatViewModel.messages.observeAsState(emptyList())
    var selectedTab by remember { mutableStateOf("Chat") }
    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Chat",
            color = Color(0xFFFF9800),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(16.dp)
        )

        LazyColumn(modifier = Modifier.weight(1f).padding(horizontal = 16.dp)) {
            items(messages) { message ->
                ChatMessageItem(message)
            }
        }

        BottomNavigationBar(navController, selectedTab) { selectedTab = it }
    }
}

@Composable
fun ChatMessageItem(message: Message) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Black),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("${message.sender}:", color = Color(0xFFFF9800), style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(message.content, color = Color.White, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

