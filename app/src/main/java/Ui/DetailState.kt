package com.example.panalsuite.ui.detail

import com.example.panalsuite.domain.model.Ticket
import com.example.panalsuite.domain.model.User

data class DetailState(
    val currentUser: User? = null,
    val ticket: Ticket? = null,
    val newComment: String = "",
    val isLoading: Boolean = false
)
