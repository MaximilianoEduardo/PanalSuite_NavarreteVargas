package com.example.panalsuite.domain.usecase

import com.example.panalsuite.domain.model.Role
import com.example.panalsuite.domain.model.Ticket
import com.example.panalsuite.domain.repository.TicketRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetTicketsUseCase(
    private val ticketRepository: TicketRepository
) {
    operator fun invoke(userRole: Role, userId: String): Flow<List<Ticket>> {
        return ticketRepository.getTickets().map { list ->
            when (userRole) {
                Role.APPLICANT -> list.filter { it.applicantId == userId }
                Role.RESOLVER -> list.filter { it.assignedTo == userId || it.assignedTo == null }
                Role.SUPERVISOR, Role.MANAGEMENT, Role.ADMIN -> list
            }
        }
    }
}