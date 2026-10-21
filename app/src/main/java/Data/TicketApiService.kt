package com.example.panalsuite.data.remote

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface TicketApiService {
    @GET("tickets")
    suspend fun getTickets(): Response<List<TicketDto>>

    @POST("tickets")
    suspend fun createTicket(@Body ticket: TicketDto): Response<TicketDto>

    @PUT("tickets/{id}/status")
    suspend fun updateStatus(@Path("id") id: String, @Body statusMap: Map<String, String>): Response<Unit>
}