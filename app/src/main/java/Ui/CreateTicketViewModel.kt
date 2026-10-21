package com.example.panalsuite.ui.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.panalsuite.domain.model.Priority
import com.example.panalsuite.domain.model.Role
import com.example.panalsuite.domain.model.Ticket
import com.example.panalsuite.domain.model.TicketStatus
import com.example.panalsuite.domain.repository.AuthRepository
import com.example.panalsuite.domain.repository.TicketRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import java.util.UUID

class CreateTicketViewModel(
    private val ticketRepository: TicketRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CreateTicketState())
    val uiState: StateFlow<CreateTicketState> = _uiState.asStateFlow()

    fun onTitleChange(newTitle: String) {
        _uiState.value = _uiState.value.copy(
            title = newTitle,
            titleError = if (newTitle.isBlank()) "Title cannot be empty" else null
        )
    }

    fun onDescriptionChange(newDesc: String) {
        _uiState.value = _uiState.value.copy(
            description = newDesc,
            descriptionError = if (newDesc.isBlank()) "Description cannot be empty" else null
        )
    }

    fun onFlowChange(flow: String) {
        _uiState.value = _uiState.value.copy(selectedFlow = flow)
    }

    fun onCategoryChange(category: String) {
        _uiState.value = _uiState.value.copy(selectedCategory = category)
    }

    fun onPriorityChange(priority: Priority) {
        _uiState.value = _uiState.value.copy(selectedPriority = priority)
    }

    fun createTicket(onSuccess: () -> Unit) {
        val currentState = _uiState.value
        val hasTitleError = currentState.title.isBlank()
        val hasDescError = currentState.description.isBlank()

        if (hasTitleError || hasDescError) {
            _uiState.value = currentState.copy(
                titleError = if (hasTitleError) "Title is required" else null,
                descriptionError = if (hasDescError) "Description is required" else null
            )
            return
        }

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            val currentUser = authRepository.getCurrentUser().firstOrNull()

            val newTicket = Ticket(
                id = "TCK-${(1000..9999).random()}",
                title = currentState.title,
                description = currentState.description,
                flowType = currentState.selectedFlow,
                category = currentState.selectedCategory,
                priority = currentState.selectedPriority,
                status = TicketStatus.OPEN,
                location = currentState.location,
                applicantId = currentUser?.id ?: "usr_guest",
                applicantRole = currentUser?.role ?: Role.APPLICANT,
                assignedTeam = "Mesa de Ayuda",
                assignedTo = null,
                createdAt = System.currentTimeMillis(),
                updatedAt = System.currentTimeMillis()
            )

            ticketRepository.createTicket(newTicket)
            _uiState.value = _uiState.value.copy(isLoading = false, isSuccess = true)
            onSuccess()
        }
    }
}