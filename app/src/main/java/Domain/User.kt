package com.example.panalsuite.domain.model

data class User(
    val id: String,
    val name: String,
    val email: String,
    val role: Role
)