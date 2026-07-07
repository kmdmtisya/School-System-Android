package com.schoolmanagement.mobile.data.api

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
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * Retrofit API contract for the local School Management System backend.
 */
interface SchoolApi {
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @GET("students/profile")
    suspend fun getStudentProfile(): StudentProfileDto

    @GET("students/timetable")
    suspend fun getStudentTimetable(): List<TimetableEntryDto>

    @GET("students/results")
    suspend fun getStudentResults(): List<ResultDto>

    @GET("students/fees")
    suspend fun getStudentFees(): FeeDto

    @GET("teachers/classes")
    suspend fun getTeacherClasses(): List<TeacherClassDto>

    @GET("teachers/timetable")
    suspend fun getTeacherTimetable(): List<TimetableEntryDto>

    @POST("teachers/attendance")
    suspend fun postAttendance(): String

    @POST("teachers/marks")
    suspend fun postMarks(): String

    @GET("parents/children")
    suspend fun getChildren(): List<ChildDto>

    @GET("parents/children/{id}/results")
    suspend fun getChildResults(@Path("id") childId: Int): List<ResultDto>

    @GET("parents/children/{id}/fees")
    suspend fun getChildFees(@Path("id") childId: Int): FeeDto

    @GET("announcements")
    suspend fun getAnnouncements(): List<AnnouncementDto>

    @GET("teachers/students")
    suspend fun getTeacherStudents(): List<StudentSummaryDto>
}
