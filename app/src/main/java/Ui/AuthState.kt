package com.example.panalsuite.ui.auth

import com.example.panalsuite.domain.model.User

data class AuthState(
    val currentUser: User? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
