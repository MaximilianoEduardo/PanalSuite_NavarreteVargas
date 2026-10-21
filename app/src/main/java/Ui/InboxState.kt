package com.example.panalsuite.ui.inbox

import com.example.panalsuite.domain.model.Ticket
import com.example.panalsuite.domain.model.User

data class InboxState(
    val currentUser: User? = null,
    val tickets: List<Ticket> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
