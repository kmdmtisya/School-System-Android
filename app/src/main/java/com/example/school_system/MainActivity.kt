package com.schoolmanagement.mobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.schoolmanagement.mobile.navigation.AppNavHost
import com.schoolmanagement.mobile.ui.theme.SchoolSystemTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SchoolSystemTheme {
                AppNavHost()
            }
        }
    }
}