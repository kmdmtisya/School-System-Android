package com.schoolmanagement.mobile.presentation.parent

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
 * Parent dashboard for linked children, performance, fees and announcements.
 */
@Composable
fun ParentDashboardScreen(
    viewModel: ParentViewModel = hiltViewModel(),
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
                    Text("Parent Dashboard", style = MaterialTheme.typography.headlineMedium)
                    Button(onClick = onLogout, modifier = Modifier.padding(vertical = 8.dp)) {
                        Text("Logout")
                    }
                }
                item { Text("Linked Children", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(top = 8.dp)) }
                items(uiState.children.size) { index ->
                    val child = uiState.children[index]
                    Text("${child.name} • ${child.className}")
                }
                item { Text("Child Performance", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(top = 8.dp)) }
                items(uiState.results.size) { index ->
                    val result = uiState.results[index]
                    Text("${result.subject}: ${result.mark} (${result.grade})")
                }
                item { Text("Fees", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(top = 8.dp)) }
                item { Text("Balance: ${uiState.fees?.balance ?: "-"}") }
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
