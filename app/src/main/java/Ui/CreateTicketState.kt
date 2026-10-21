package com.example.panalsuite.ui.create

import com.example.panalsuite.domain.model.Priority

data class CreateTicketState(
    val title: String = "",
    val titleError: String? = null,
    val description: String = "",
    val descriptionError: String? = null,
    val selectedFlow: String = "Soporte Infraestructura",
    val selectedCategory: String = "Mesa de Ayuda",
    val selectedPriority: Priority = Priority.MEDIUM,
    val location: String = "Sucursal Alameda",
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false
)
