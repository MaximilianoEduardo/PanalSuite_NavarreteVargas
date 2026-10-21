package com.example.panalsuite.domain.usecase

import com.example.panalsuite.domain.model.Ticket
import com.example.panalsuite.domain.repository.TicketRepository

class CreateTicketUseCase(
    private val ticketRepository: TicketRepository
) {
    suspend operator fun invoke(ticket: Ticket): Result<Unit> {
        if (ticket.title.isBlank()) {
            return Result.failure(IllegalArgumentException("Title cannot be empty"))
        }
        if (ticket.description.isBlank()) {
            return Result.failure(IllegalArgumentException("Description cannot be empty"))
        }
        val success = ticketRepository.createTicket(ticket)
        return if (success) {
            Result.success(Unit)
        } else {
            Result.failure(Exception("Failed to save ticket"))
        }
    }
}