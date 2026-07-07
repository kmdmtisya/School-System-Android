package com.schoolmanagement.mobile.data.model

import com.google.gson.annotations.SerializedName

/**
 * Authentication request body used for login.
 */
data class LoginRequest(
    val username: String,
    val password: String
)

/**
 * Authentication response returned by the backend.
 */
data class LoginResponse(
    val token: String,
    val user: UserDto,
    val role: String
)

/**
 * Minimal user representation shared across the app.
 */
data class UserDto(
    @SerializedName("id") val id: Int? = null,
    @SerializedName("fullName") val fullName: String = "",
    @SerializedName("email") val email: String = "",
    @SerializedName("role") val role: String = ""
)

/**
 * Student profile details surfaced on the student dashboard.
 */
data class StudentProfileDto(
    val fullName: String = "",
    val studentId: String = "",
    val className: String = "",
    val phoneNumber: String = ""
)

/**
 * Timetable entry used by students and teachers.
 */
data class TimetableEntryDto(
    val day: String = "",
    val subject: String = "",
    val time: String = "",
    val room: String = ""
)

/**
 * Academic result entry shown to students and parents.
 */
data class ResultDto(
    val subject: String = "",
    val mark: String = "",
    val grade: String = ""
)

/**
 * Fee balance summary used by students and parents.
 */
data class FeeDto(
    val balance: String = "",
    val dueDate: String = ""
)

/**
 * Announcement payload shared across roles.
 */
data class AnnouncementDto(
    val title: String = "",
    val message: String = "",
    val createdAt: String = ""
)

/**
 * Teacher class assignment surfaced on the teacher dashboard.
 */
data class TeacherClassDto(
    val className: String = "",
    val section: String = "",
    val subject: String = ""
)

/**
 * Student summary card shown on the teacher dashboard.
 */
data class StudentSummaryDto(
    val id: Int = 0,
    val name: String = "",
    val className: String = ""
)

/**
 * Parent child profile shown on the parent dashboard.
 */
data class ChildDto(
    val id: Int = 0,
    val name: String = "",
    val className: String = ""
)
