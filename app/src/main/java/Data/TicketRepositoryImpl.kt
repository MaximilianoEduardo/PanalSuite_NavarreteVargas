package com.example.panalsuite.data.repository

import com.example.panalsuite.data.local.TicketDao
import com.example.panalsuite.data.local.TicketEntity
import com.example.panalsuite.domain.model.Comment
import com.example.panalsuite.domain.model.Evidence
import com.example.panalsuite.domain.model.Priority
import com.example.panalsuite.domain.model.Role
import com.example.panalsuite.domain.model.Ticket
import com.example.panalsuite.domain.model.TicketStatus
import com.example.panalsuite.domain.repository.TicketRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TicketRepositoryImpl(
    private val ticketDao: TicketDao
) : TicketRepository {

    private val inMemoryComments = mutableMapOf<String, MutableList<Comment>>()
    private val inMemoryEvidences = mutableMapOf<String, MutableList<Evidence>>()

    override fun getTickets(): Flow<List<Ticket>> {
        return ticketDao.getAllTickets().map { entities ->
            entities.map { entity ->
                entity.toDomain(
                    comments = inMemoryComments[entity.id] ?: emptyList(),
                    evidences = inMemoryEvidences[entity.id] ?: emptyList()
                )
            }
        }
    }

    override fun getTicketById(id: String): Flow<Ticket?> {
        return ticketDao.getTicketById(id).map { entity ->
            entity?.toDomain(
                comments = inMemoryComments[id] ?: emptyList(),
                evidences = inMemoryEvidences[id] ?: emptyList()
            )
        }
    }

    override suspend fun createTicket(ticket: Ticket): Boolean {
        ticketDao.insertTicket(ticket.toEntity())
        return true
    }

    override suspend fun updateTicketStatus(ticketId: String, newStatus: TicketStatus): Boolean {
        ticketDao.updateStatus(ticketId, newStatus.name, System.currentTimeMillis())
        return true
    }

    override suspend fun addComment(ticketId: String, comment: Comment): Boolean {
        val list = inMemoryComments.getOrPut(ticketId) { mutableListOf() }
        list.add(comment)
        return true
    }

    override suspend fun addEvidence(ticketId: String, evidence: Evidence): Boolean {
        val list = inMemoryEvidences.getOrPut(ticketId) { mutableListOf() }
        list.add(evidence)
        return true
    }

    private fun TicketEntity.toDomain(comments: List<Comment>, evidences: List<Evidence>) = Ticket(
        id = id,
        title = title,
        description = description,
        flowType = flowType,
        category = category,
        priority = priority,
        status = status,
        location = location,
        applicantId = applicantId,
        applicantRole = applicantRole,
        assignedTeam = assignedTeam,
        assignedTo = assignedTo,
        createdAt = createdAt,
        updatedAt = updatedAt,
        comments = comments,
        evidences = evidences
    )

    private fun Ticket.toEntity() = TicketEntity(
        id = id,
        title = title,
        description = description,
        flowType = flowType,
        category = category,
        priority = priority,
        status = status,
        location = location,
        applicantId = applicantId,
        applicantRole = applicantRole,
        assignedTeam = assignedTeam,
        assignedTo = assignedTo,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}