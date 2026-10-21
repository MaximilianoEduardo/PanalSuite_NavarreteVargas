package com.example.panalsuite.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.panalsuite.domain.repository.AuthRepository
import com.example.panalsuite.domain.repository.TicketRepository
import com.example.panalsuite.ui.auth.LoginScreen
import com.example.panalsuite.ui.auth.LoginViewModel
import com.example.panalsuite.ui.create.CreateTicketScreen
import com.example.panalsuite.ui.create.CreateTicketViewModel
import com.example.panalsuite.ui.detail.DetailScreen
import com.example.panalsuite.ui.detail.DetailViewModel
import com.example.panalsuite.ui.inbox.InboxScreen
import com.example.panalsuite.ui.inbox.InboxViewModel

@Composable
fun AppNavigation(
    navController: NavHostController,
    ticketRepository: TicketRepository,
    authRepository: AuthRepository
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {
        composable(Screen.Login.route) {
            val viewModel = LoginViewModel(authRepository)
            LoginScreen(
                viewModel = viewModel,
                onLoginSuccess = {
                    navController.navigate(Screen.Inbox.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Inbox.route) {
            val viewModel = InboxViewModel(ticketRepository, authRepository)
            InboxScreen(
                viewModel = viewModel,
                onCreateClick = { navController.navigate(Screen.CreateTicket.route) },
                onTicketClick = { ticketId ->
                    navController.navigate(Screen.Detail.createRoute(ticketId))
                },
                onLogoutClick = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Inbox.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.CreateTicket.route) {
            val viewModel = CreateTicketViewModel(ticketRepository, authRepository)
            CreateTicketScreen(
                viewModel = viewModel,
                onBackClick = { navController.popBackStack() },
                onTicketCreated = { navController.popBackStack() }
            )
        }

        composable(
            route = Screen.Detail.route,
            arguments = listOf(navArgument("ticketId") { type = NavType.StringType })
        ) { backStackEntry ->
            val ticketId = backStackEntry.arguments?.getString("ticketId") ?: ""
            val viewModel = DetailViewModel(ticketId, ticketRepository, authRepository)
            DetailScreen(
                viewModel = viewModel,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}