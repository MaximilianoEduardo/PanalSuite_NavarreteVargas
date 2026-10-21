package com.example.panalsuite.domain.repository

import com.example.panalsuite.domain.model.Comment
import com.example.panalsuite.domain.model.Evidence
import com.example.panalsuite.domain.model.Ticket
import com.example.panalsuite.domain.model.TicketStatus
import kotlinx.coroutines.flow.Flow

interface TicketRepository {
    fun getTickets(): Flow<List<Ticket>>
    fun getTicketById(id: String): Flow<Ticket?>
    suspend fun createTicket(ticket: Ticket): Boolean
    suspend fun updateTicketStatus(ticketId: String, newStatus: TicketStatus): Boolean
    suspend fun addComment(ticketId: String, comment: Comment): Boolean
    suspend fun addEvidence(ticketId: String, evidence: Evidence): Boolean
}