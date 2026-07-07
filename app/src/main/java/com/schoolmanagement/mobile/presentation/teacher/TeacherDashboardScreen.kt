package com.schoolmanagement.mobile.presentation.teacher

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
 * Teacher dashboard for assigned classes, students, attendance and marks.
 */
@Composable
fun TeacherDashboardScreen(
    viewModel: TeacherViewModel = hiltViewModel(),
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
            LazyColumn(modifier = Modifier.padding(paddingValues).padding(16.dp)) {
                item {
                    Text("Teacher Dashboard", style = MaterialTheme.typography.headlineMedium)
                    Button(onClick = onLogout, modifier = Modifier.padding(vertical = 8.dp)) {
                        Text("Logout")
                    }
                }
                item { Text("Assigned Classes", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(top = 8.dp)) }
                items(uiState.classes.size) { index ->
                    val cls = uiState.classes[index]
                    Text("${cls.className} • ${cls.section} • ${cls.subject}")
                }
                item { Text("Students", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(top = 8.dp)) }
                items(uiState.students.size) { index ->
                    val student = uiState.students[index]
                    Text("${student.name} • ${student.className}")
                }
                item { Text("Timetable", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(top = 8.dp)) }
                items(uiState.timetable.size) { index ->
                    val entry = uiState.timetable[index]
                    Text("${entry.day} • ${entry.subject} • ${entry.time}")
                }
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
