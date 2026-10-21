package com.example.panalsuite.domain.repository

import com.example.panalsuite.domain.model.Role
import com.example.panalsuite.domain.model.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun getCurrentUser(): Flow<User?>
    suspend fun loginAsRole(role: Role): User
    suspend fun logout()
}