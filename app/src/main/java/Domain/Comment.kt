package com.example.panalsuite.domain.model

data class Comment(
    val id: String,
    val ticketId: String,
    val authorName: String,
    val authorRole: Role,
    val content: String,
    val createdAt: Long
)
