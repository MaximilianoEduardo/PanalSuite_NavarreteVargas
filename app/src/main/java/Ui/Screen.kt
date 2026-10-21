package com.example.panalsuite.ui.navigation

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Inbox : Screen("inbox")
    object CreateTicket : Screen("create_ticket")
    object Detail : Screen("detail/{ticketId}") {
        fun createRoute(ticketId: String) = "detail/$ticketId"
    }
}