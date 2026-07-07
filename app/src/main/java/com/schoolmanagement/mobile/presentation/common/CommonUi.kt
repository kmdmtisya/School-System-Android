package com.schoolmanagement.mobile.presentation.common

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Lightweight reusable UI for showing inline errors.
 */
@Composable
fun ErrorBanner(message: String, modifier: Modifier = Modifier) {
    Card(modifier = modifier.padding(top = 12.dp)) {
        Text(text = message, modifier = Modifier.padding(12.dp))
    }
}
