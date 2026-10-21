package com.example.panalsuite.domain.model

data class Ticket(
    val id: String,
    val title: String,
    val description: String,
    val flowType: String,
    val category: String,
    val priority: Priority,
    val status: TicketStatus,
    val location: String?,
    val applicantId: String,
    val applicantRole: Role,
    val assignedTeam: String?,
    val assignedTo: String?,
    val createdAt: Long,
    val updatedAt: Long,
    val comments: List<Comment> = emptyList(),
    val evidences: List<Evidence> = emptyList()
)
