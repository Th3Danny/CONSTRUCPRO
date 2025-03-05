package com.example.myapplication.home.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.core.navigation.BottomNavigationBar
import com.example.myapplication.home.data.model.Notification

@Composable
fun NotificationScreen(navController: NavController) {
    val fakeNotifications = listOf(
        Notification("1", "Reunión programada", "Nueva reunión el viernes."),
        Notification("2", "Avance del proyecto", "Se ha completado el 80% del trabajo."),
        Notification("3", "Nueva actualización", "Se han agregado nuevas funciones.")
    )

    var selectedTab by remember { mutableStateOf("Notificaciones") }

    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn(modifier = Modifier.weight(1f).padding(16.dp)) {
            items(fakeNotifications) { notification ->
                NotificationItem(notification) // ✅ Ahora pasa correctamente el objeto `Notification`
            }
        }
        BottomNavigationBar(navController, selectedTab) { selectedTab = it }
    }
}

// ✅ Corrección: `notification` ahora es de tipo `Notification`
@Composable
fun NotificationItem(notification: Notification) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.DarkGray)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = notification.title, color = Color.White, style = MaterialTheme.typography.titleMedium) // ✅ Ahora usa correctamente `notification.title`
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = notification.description, color = Color.LightGray, style = MaterialTheme.typography.bodyMedium) // ✅ Ahora usa correctamente `notification.description`
        }
    }
}
