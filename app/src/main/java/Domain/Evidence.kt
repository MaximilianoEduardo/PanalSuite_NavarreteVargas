package com.example.panalsuite.domain.model

data class Evidence(
    val id: String,
    val ticketId: String,
    val imageUri: String?,
    val description: String,
    val locationName: String?,
    val latitude: Double?,
    val longitude: Double?,
    val timestamp: Long
)