package com.example.panalsuite.data.repository

import com.example.panalsuite.domain.model.Role
import com.example.panalsuite.domain.model.User
import com.example.panalsuite.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class AuthRepositoryImpl : AuthRepository {

    private val currentUserState = MutableStateFlow<User?>(
        User(
            id = "usr_applicant",
            name = "Juan Pérez (Solicitante)",
            email = "juan.perez@panalbit.cl",
            role = Role.APPLICANT
        )
    )

    override fun getCurrentUser(): Flow<User?> = currentUserState

    override suspend fun loginAsRole(role: Role): User {
        val user = when (role) {
            Role.APPLICANT -> User("usr_applicant", "Juan Pérez (Solicitante)", "applicant@panalbit.cl", Role.APPLICANT)
            Role.RESOLVER -> User("usr_resolver", "María López (Resolutor)", "resolver@panalbit.cl", Role.RESOLVER)
            Role.SUPERVISOR -> User("usr_supervisor", "Carlos Soto (Supervisor)", "supervisor@panalbit.cl", Role.SUPERVISOR)
            Role.MANAGEMENT -> User("usr_mgmt", "Elena Torres (Gerencia)", "gerencia@panalbit.cl", Role.MANAGEMENT)
            Role.ADMIN -> User("usr_admin", "Admin Sistema", "admin@panalbit.cl", Role.ADMIN)
        }
        currentUserState.value = user
        return user
    }

    override suspend fun logout() {
        currentUserState.value = null
    }
}