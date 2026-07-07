package com.schoolmanagement.mobile.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.schoolmanagement.mobile.data.api.SchoolApi
import com.schoolmanagement.mobile.data.model.AnnouncementDto
import com.schoolmanagement.mobile.data.model.ChildDto
import com.schoolmanagement.mobile.data.model.FeeDto
import com.schoolmanagement.mobile.data.model.LoginRequest
import com.schoolmanagement.mobile.data.model.LoginResponse
import com.schoolmanagement.mobile.data.model.ResultDto
import com.schoolmanagement.mobile.data.model.StudentProfileDto
import com.schoolmanagement.mobile.data.model.StudentSummaryDto
import com.schoolmanagement.mobile.data.model.TeacherClassDto
import com.schoolmanagement.mobile.data.model.TimetableEntryDto
import com.schoolmanagement.mobile.utils.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Repository for authentication and role-based data access.
 */
class SchoolRepository(
    private val api: SchoolApi,
    private val dataStore: DataStore<Preferences>
) {
    suspend fun login(username: String, password: String): LoginResponse =
        api.login(LoginRequest(username, password))

    suspend fun saveSession(token: String, role: String, name: String) {
        dataStore.edit { preferences ->
            preferences[stringPreferencesKey(Constants.AUTH_TOKEN_KEY)] = token
            preferences[stringPreferencesKey(Constants.USER_ROLE_KEY)] = role
            preferences[stringPreferencesKey(Constants.USER_NAME_KEY)] = name
        }
    }

    fun getSessionRole(): Flow<String?> = dataStore.data.map { preferences ->
        preferences[stringPreferencesKey(Constants.USER_ROLE_KEY)]
    }

    fun getSessionName(): Flow<String?> = dataStore.data.map { preferences ->
        preferences[stringPreferencesKey(Constants.USER_NAME_KEY)]
    }

    suspend fun clearSession() {
        dataStore.edit { it.clear() }
    }

    suspend fun getStudentProfile(): StudentProfileDto = api.getStudentProfile()
    suspend fun getStudentTimetable(): List<TimetableEntryDto> = api.getStudentTimetable()
    suspend fun getStudentResults(): List<ResultDto> = api.getStudentResults()
    suspend fun getStudentFees(): FeeDto = api.getStudentFees()

    suspend fun getTeacherClasses(): List<TeacherClassDto> = api.getTeacherClasses()
    suspend fun getTeacherTimetable(): List<TimetableEntryDto> = api.getTeacherTimetable()
    suspend fun getTeacherStudents(): List<StudentSummaryDto> = api.getTeacherStudents()
    suspend fun postAttendance(): String = api.postAttendance()
    suspend fun postMarks(): String = api.postMarks()

    suspend fun getChildren(): List<ChildDto> = api.getChildren()
    suspend fun getChildResults(childId: Int): List<ResultDto> = api.getChildResults(childId)
    suspend fun getChildFees(childId: Int): FeeDto = api.getChildFees(childId)
    suspend fun getAnnouncements(): List<AnnouncementDto> = api.getAnnouncements()
}
