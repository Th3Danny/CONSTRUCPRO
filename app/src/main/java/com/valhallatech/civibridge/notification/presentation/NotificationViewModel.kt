package com.valhallatech.civibridge.notification.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.valhallatech.civibridge.notification.data.model.Notification
import com.valhallatech.civibridge.notification.domain.GetNotificationsUseCase
import kotlinx.coroutines.launch

class NotificationViewModel(
    private val getNotificationsUseCase: GetNotificationsUseCase,
    //private val markNotificationAsReadUseCase: MarkNotificationAsReadUseCase? = null
) : ViewModel() {

    // Lista de notificaciones
    private val _notifications = MutableLiveData<List<Notification>>()
    val notifications: LiveData<List<Notification>> = _notifications

    // Estado de carga
    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> = _isLoading

    // Estado de error
    private val _error = MutableLiveData<String>("")
    val error: LiveData<String> = _error

    init {
        loadNotifications()
    }

    /**
     * Carga las notificaciones del usuario
     */
    fun loadNotifications() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _error.value = ""

                val result = getNotificationsUseCase()
                _notifications.value = result.getOrDefault(emptyList())

                _isLoading.value = false
            } catch (e: Exception) {
                _error.value = e.message ?: "Error desconocido"
                _notifications.value = emptyList()
                _isLoading.value = false
            }
        }
    }

    /**
     * Marca una notificación como leída
     * (Solo si está disponible el caso de uso)
     */
//    fun markAsRead(notificationId: String) {
//        viewModelScope.launch {
//            markNotificationAsReadUseCase?.let { useCase ->
//                try {
//                    useCase(notificationId)
//
//                    // Actualiza la lista local de notificaciones
//                    _notifications.value = _notifications.value?.map { notification ->
//                        if (notification.id == notificationId) {
//                            notification.copy(read = true)
//                        } else {
//                            notification
//                        }
//                    }
//                } catch (e: Exception) {
//                    // Si hay un error al marcar como leída, simplemente lo ignoramos
//                    // y mantenemos la UI actualizada para una mejor experiencia
//                }
//            }
//        }
//    }
}