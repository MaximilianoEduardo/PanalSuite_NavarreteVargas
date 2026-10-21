package com.example.panalsuite.ui.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.panalsuite.domain.model.Role
import com.example.panalsuite.domain.model.TicketStatus
import com.example.panalsuite.util.DateFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    viewModel: DetailViewModel,
    onBackClick: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()
    val ticket = state.ticket

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(ticket?.id ?: "Ticket Detail") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        if (ticket != null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = ticket.title,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Status: ${ticket.status.name} | Priority: ${ticket.priority.name}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = ticket.description,
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Acciones para Resolutor / Supervisor
                if (state.currentUser?.role == Role.RESOLVER || state.currentUser?.role == Role.SUPERVISOR) {
                    Text(
                        text = "Update Status:",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Row {
                        OutlinedButton(
                            onClick = { viewModel.updateStatus(TicketStatus.IN_PROGRESS) },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("In Progress")
                        }
                        OutlinedButton(
                            onClick = { viewModel.updateStatus(TicketStatus.RESOLVED) },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Resolve")
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = { viewModel.addSimulatedEvidence() },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Add Field Evidence (Photo + GPS)")
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                }

                // Evidencias registradas
                if (ticket.evidences.isNotEmpty()) {
                    Text(
                        text = "Evidences (${ticket.evidences.size}):",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    ticket.evidences.forEach { ev ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                        ) {
                            Column(modifier = Modifier.padding(8.dp)) {
                                Text(ev.description, fontWeight = FontWeight.Bold)
                                if (ev.locationName != null) {
                                    Text("Location: ${ev.locationName}", style = MaterialTheme.typography.bodySmall)
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }

                // Registro de Comentarios
                Text(
                    text = "Comments & History:",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                ticket.comments.forEach { comment ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(
                                text = "${comment.authorName} (${comment.authorRole.name})",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Text(text = comment.content, style = MaterialTheme.typography.bodyMedium)
                            Text(
                                text = DateFormatter.formatTimestamp(comment.createdAt),
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = state.newComment,
                    onValueChange = { viewModel.onCommentChange(it) },
                    label = { Text("Add comment / update note") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = { viewModel.addComment() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Post Comment")
                }
            }
        }
    }
}