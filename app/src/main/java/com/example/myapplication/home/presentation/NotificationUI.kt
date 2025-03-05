package com.example.myapplication.home.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.myapplication.core.navigation.BottomNavigationBar
import com.example.myapplication.home.data.model.Notification

@Composable
fun NotificationScreen(navController: NavController, notificationViewModel: NotificationViewModel = viewModel()) {
    val notifications by notificationViewModel.notifications.observeAsState(emptyList())
    var selectedTab by remember { mutableStateOf("Notificaciones") }
    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Notificaciones",
            color = Color(0xFFFF9800),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(16.dp)
        )

        LazyColumn(modifier = Modifier.weight(1f).padding(horizontal = 16.dp)) {
            items(notifications) { notification ->
                NotificationItem(notification)
            }
        }

        BottomNavigationBar(navController, selectedTab) { selectedTab = it }
    }
}

@Composable
fun NotificationItem(notification: Notification) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Black),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(notification.title, color = Color(0xFFFF9800), style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(notification.description, color = Color.White, style = MaterialTheme.typography.bodyMedium)
        }
    }
}
