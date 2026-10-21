package com.example.panalsuite.data.remote

data class TicketDto(
    val id: String,
    val title: String,
    val description: String,
    val flowType: String,
    val category: String,
    val priority: String,
    val status: String,
    val location: String?,
    val applicantId: String,
    val applicantRole: String,
    val assignedTeam: String?,
    val assignedTo: String?,
    val createdAt: Long,
    val updatedAt: Long
)