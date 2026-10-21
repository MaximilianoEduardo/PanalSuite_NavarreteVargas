package com.example.panalsuite.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.panalsuite.domain.model.Comment
import com.example.panalsuite.domain.model.Evidence
import com.example.panalsuite.domain.model.Role
import com.example.panalsuite.domain.model.TicketStatus
import com.example.panalsuite.domain.repository.AuthRepository
import com.example.panalsuite.domain.repository.TicketRepository
import com.example.panalsuite.util.LocationSimulator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import java.util.UUID

class DetailViewModel(
    private val ticketId: String,
    private val ticketRepository: TicketRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(DetailState())
    val uiState: StateFlow<DetailState> = _uiState.asStateFlow()

    init {
        loadTicket()
    }

    private fun loadTicket() {
        viewModelScope.launch {
            combine(
                authRepository.getCurrentUser(),
                ticketRepository.getTicketById(ticketId)
            ) { user, ticket ->
                DetailState(currentUser = user, ticket = ticket)
            }.collect { state ->
                _uiState.value = state
            }
        }
    }

    fun onCommentChange(text: String) {
        _uiState.value = _uiState.value.copy(newComment = text)
    }

    fun addComment() {
        val currentState = _uiState.value
        val user = currentState.currentUser ?: return
        if (currentState.newComment.isBlank()) return

        viewModelScope.launch {
            val comment = Comment(
                id = UUID.randomUUID().toString(),
                ticketId = ticketId,
                authorName = user.name,
                authorRole = user.role,
                content = currentState.newComment,
                createdAt = System.currentTimeMillis()
            )
            ticketRepository.addComment(ticketId, comment)
            _uiState.value = _uiState.value.copy(newComment = "")
        }
    }

    fun updateStatus(newStatus: TicketStatus) {
        viewModelScope.launch {
            ticketRepository.updateTicketStatus(ticketId, newStatus)
        }
    }

    fun addSimulatedEvidence() {
        val user = _uiState.value.currentUser ?: return
        val randomLoc = LocationSimulator.getRandomLocation()

        viewModelScope.launch {
            val evidence = Evidence(
                id = UUID.randomUUID().toString(),
                ticketId = ticketId,
                imageUri = "mock://evidence_photo.jpg",
                description = "Field evidence registered by ${user.name}",
                locationName = randomLoc.name,
                latitude = randomLoc.latitude,
                longitude = randomLoc.longitude,
                timestamp = System.currentTimeMillis()
            )
            ticketRepository.addEvidence(ticketId, evidence)
        }
    }
}