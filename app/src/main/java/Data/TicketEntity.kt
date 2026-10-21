package com.example.panalsuite.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.panalsuite.domain.model.Priority
import com.example.panalsuite.domain.model.Role
import com.example.panalsuite.domain.model.TicketStatus

@Entity(tableName = "tickets")
data class TicketEntity(
    @PrimaryKey val id: String,
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
    val updatedAt: Long
)
