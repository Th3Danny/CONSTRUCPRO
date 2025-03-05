package com.example.myapplication.home.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.home.data.model.Notification
import com.example.myapplication.home.domain.GetNotificationsUseCase
import kotlinx.coroutines.launch

class NotificationViewModel(private val getNotificationsUseCase: GetNotificationsUseCase) : ViewModel() {

    private val _notifications = MutableLiveData<List<Notification>>()
    val notifications: LiveData<List<Notification>> = _notifications

    init {
        loadNotifications()
    }

    private fun loadNotifications() {
        viewModelScope.launch {
            try {
                // ⚡ Simulación de datos falsos
                _notifications.value = listOf(
                    Notification("Actualización del proyecto", "Se han agregado nuevos planos", "12:00 PM"),
                    Notification("Nuevo mensaje", "Tienes un mensaje sin leer", "12:30 PM"),
                    Notification("Revisión de seguridad", "La inspección se completó con éxito", "1:00 PM")
                )

                // Para la API:
                // val result = getNotificationsUseCase()
                // _notifications.value = result.getOrDefault(emptyList())
            } catch (e: Exception) {
                _notifications.value = emptyList()
            }
        }
    }
}
