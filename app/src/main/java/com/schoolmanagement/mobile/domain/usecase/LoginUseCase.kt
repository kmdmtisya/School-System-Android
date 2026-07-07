package com.schoolmanagement.mobile.domain.usecase

import com.schoolmanagement.mobile.data.model.LoginResponse
import com.schoolmanagement.mobile.data.repository.SchoolRepository

/**
 * Use case for authenticating a user against the backend.
 */
class LoginUseCase(
    private val repository: SchoolRepository
) {
    suspend operator fun invoke(username: String, password: String): LoginResponse =
        repository.login(username, password)
}
