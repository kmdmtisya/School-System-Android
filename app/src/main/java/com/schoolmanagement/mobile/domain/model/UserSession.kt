package com.schoolmanagement.mobile.domain.model

/**
 * Simple domain model for the current authenticated session.
 */
data class UserSession(
    val role: String,
    val name: String
)
