package com.schoolmanagement.mobile.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.schoolmanagement.mobile.presentation.auth.AuthViewModel
import com.schoolmanagement.mobile.presentation.auth.LoginScreen
import com.schoolmanagement.mobile.presentation.parent.ParentDashboardScreen
import com.schoolmanagement.mobile.presentation.student.StudentDashboardScreen
import com.schoolmanagement.mobile.presentation.teacher.TeacherDashboardScreen

/**
 * Navigation graph that routes users after login based on their role.
 */
@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    val authViewModel: AuthViewModel = hiltViewModel()
    val authState by authViewModel.uiState.collectAsState()

    NavHost(
        navController = navController,
        startDestination = if (authState.isAuthenticated) {
            when (authState.role.lowercase()) {
                "student" -> Route.STUDENT_DASHBOARD
                "teacher" -> Route.TEACHER_DASHBOARD
                "parent" -> Route.PARENT_DASHBOARD
                else -> Route.LOGIN
            }
        } else {
            Route.LOGIN
        }
    ) {
        composable(Route.LOGIN) {
            LoginScreen(onLoginSuccess = { role ->
                when (role.lowercase()) {
                    "student" -> navController.navigate(Route.STUDENT_DASHBOARD) {
                        popUpTo(Route.LOGIN) { inclusive = true }
                    }
                    "teacher" -> navController.navigate(Route.TEACHER_DASHBOARD) {
                        popUpTo(Route.LOGIN) { inclusive = true }
                    }
                    "parent" -> navController.navigate(Route.PARENT_DASHBOARD) {
                        popUpTo(Route.LOGIN) { inclusive = true }
                    }
                    else -> navController.navigate(Route.LOGIN)
                }
            })
        }
        composable(Route.STUDENT_DASHBOARD) {
            StudentDashboardScreen(onLogout = {
                authViewModel.logout()
                navController.navigate(Route.LOGIN) {
                    popUpTo(0) { inclusive = true }
                }
            })
        }
        composable(Route.TEACHER_DASHBOARD) {
            TeacherDashboardScreen(onLogout = {
                authViewModel.logout()
                navController.navigate(Route.LOGIN) {
                    popUpTo(0) { inclusive = true }
                }
            })
        }
        composable(Route.PARENT_DASHBOARD) {
            ParentDashboardScreen(onLogout = {
                authViewModel.logout()
                navController.navigate(Route.LOGIN) {
                    popUpTo(0) { inclusive = true }
                }
            })
        }
    }
}

object Route {
    const val LOGIN = "login"
    const val STUDENT_DASHBOARD = "student_dashboard"
    const val TEACHER_DASHBOARD = "teacher_dashboard"
    const val PARENT_DASHBOARD = "parent_dashboard"
}
