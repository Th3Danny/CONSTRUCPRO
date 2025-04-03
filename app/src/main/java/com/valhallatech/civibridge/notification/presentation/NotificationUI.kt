package com.valhallatech.civibridge.notification.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.valhallatech.civibridge.components.footer.BottomNavigationBar
import com.valhallatech.civibridge.notification.presentation.notificationComponent.EmptyNotifications
import com.valhallatech.civibridge.notification.presentation.notificationComponent.NotificationItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationScreen(
    navController: NavController,
    notificationViewModel: NotificationViewModel = viewModel()
) {
    val notifications by notificationViewModel.notifications.observeAsState(emptyList())
    var selectedTab by remember { mutableStateOf("Notificaciones") }

    // Estado de carga
    val isLoading by notificationViewModel.isLoading.observeAsState(false)

    // Estado de error
    val error by notificationViewModel.error.observeAsState("")

    // Efecto para cargar las notificaciones al inicio
    LaunchedEffect(Unit) {
        notificationViewModel.loadNotifications()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Notificaciones",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        bottomBar = {
            BottomNavigationBar(navController, selectedTab) { selectedTab = it }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background)
        ) {
            when {
                isLoading -> {
                    // Estado de carga
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                    }
                }

                error.isNotEmpty() -> {
                    // Estado de error
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.errorContainer
                            )
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    "Error al cargar notificaciones",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = MaterialTheme.colorScheme.error
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    error,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onErrorContainer
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                Button(
                                    onClick = { notificationViewModel.loadNotifications() },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = MaterialTheme.colorScheme.primary
                                    )
                                ) {
                                    Text("Reintentar")
                                }
                            }
                        }
                    }
                }

                notifications.isEmpty() -> {
                    // Sin notificaciones
                    EmptyNotifications()
                }

                else -> {
                    // Lista de notificaciones
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp)
                    ) {
                        item {
                            Spacer(modifier = Modifier.height(8.dp))
                        }

                        items(notifications) { notification ->
                            NotificationItem(notification) {

                                navController.navigate("JobInfo")
                            }
                        }

                        item {
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }
                }
            }
        }
    }
}




