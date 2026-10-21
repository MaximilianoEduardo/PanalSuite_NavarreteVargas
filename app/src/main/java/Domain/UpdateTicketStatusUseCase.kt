package com.example.panalsuite.domain.usecase

import com.example.panalsuite.domain.model.Comment
import com.example.panalsuite.domain.model.Evidence
import com.example.panalsuite.domain.model.TicketStatus
import com.example.panalsuite.domain.repository.TicketRepository

class UpdateTicketStatusUseCase(
    private val ticketRepository: TicketRepository
) {
    suspend operator fun invoke(
        ticketId: String,
        newStatus: TicketStatus,
        comment: Comment? = null,
        evidence: Evidence? = null
    ): Boolean {
        val success = ticketRepository.updateTicketStatus(ticketId, newStatus)
        if (success && comment != null) {
            ticketRepository.addComment(ticketId, comment)
        }
        if (success && evidence != null) {
            ticketRepository.addEvidence(ticketId, evidence)
        }
        return success
    }
}