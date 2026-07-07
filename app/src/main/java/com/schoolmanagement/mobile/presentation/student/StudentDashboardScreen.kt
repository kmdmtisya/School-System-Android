package com.schoolmanagement.mobile.presentation.student

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.schoolmanagement.mobile.presentation.common.ErrorBanner

/**
 * Student dashboard with profile, timetable, results, fees and announcements.
 */
@Composable
fun StudentDashboardScreen(
    viewModel: StudentViewModel = hiltViewModel(),
    onLogout: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadDashboard()
    }

    Scaffold { paddingValues ->
        if (uiState.isLoading) {
            CircularProgressIndicator(modifier = Modifier.padding(24.dp))
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                item {
                    Text("Student Dashboard", style = MaterialTheme.typography.headlineMedium)
                    Text("Welcome ${uiState.profile?.fullName ?: "Student"}")
                    Button(onClick = onLogout, modifier = Modifier.padding(vertical = 8.dp)) {
                        Text("Logout")
                    }
                }
                item { Text("Profile", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(top = 8.dp)) }
                item { Text("Name: ${uiState.profile?.fullName ?: "-"}") }
                item { Text("Class: ${uiState.profile?.className ?: "-"}") }
                item { Text("Student ID: ${uiState.profile?.studentId ?: "-"}") }
                item { Text("Timetable", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(top = 8.dp)) }
                items(uiState.timetable.size) { index ->
                    val entry = uiState.timetable[index]
                    Text("${entry.day} • ${entry.subject} • ${entry.time} • ${entry.room}")
                }
                item { Text("Results", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(top = 8.dp)) }
                items(uiState.results.size) { index ->
                    val result = uiState.results[index]
                    Text("${result.subject}: ${result.mark} (${result.grade})")
                }
                item { Text("Fees", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(top = 8.dp)) }
                item { Text("Balance: ${uiState.fees?.balance ?: "-"}") }
                item { Text("Due Date: ${uiState.fees?.dueDate ?: "-"}") }
                item { Text("Announcements", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(top = 8.dp)) }
                items(uiState.announcements.size) { index ->
                    val announcement = uiState.announcements[index]
                    Text("${announcement.title}: ${announcement.message}")
                }
                item { uiState.error?.let { ErrorBanner(message = it) } }
            }
        }
    }
}
