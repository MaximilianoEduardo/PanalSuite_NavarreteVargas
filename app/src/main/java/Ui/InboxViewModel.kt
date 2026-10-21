package com.example.panalsuite.ui.inbox

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.panalsuite.domain.model.Role
import com.example.panalsuite.domain.repository.AuthRepository
import com.example.panalsuite.domain.repository.TicketRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class InboxViewModel(
    private val ticketRepository: TicketRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(InboxState())
    val uiState: StateFlow<InboxState> = _uiState.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            combine(
                authRepository.getCurrentUser(),
                ticketRepository.getTickets()
            ) { user, tickets ->
                val filteredTickets = when (user?.role) {
                    Role.APPLICANT -> tickets.filter { it.applicantId == user.id }
                    Role.RESOLVER -> tickets.filter { it.assignedTo == user.id || it.assignedTo == null }
                    else -> tickets
                }
                InboxState(
                    currentUser = user,
                    tickets = filteredTickets,
                    isLoading = false
                )
            }.collect { state ->
                _uiState.value = state
            }
        }
    }

    fun logout(onLogout: () -> Unit) {
        viewModelScope.launch {
            authRepository.logout()
            onLogout()
        }
    }
}