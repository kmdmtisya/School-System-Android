package com.schoolmanagement.mobile.presentation.teacher

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.schoolmanagement.mobile.data.model.AnnouncementDto
import com.schoolmanagement.mobile.data.model.StudentSummaryDto
import com.schoolmanagement.mobile.data.model.TeacherClassDto
import com.schoolmanagement.mobile.data.model.TimetableEntryDto
import com.schoolmanagement.mobile.data.repository.SchoolRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TeacherViewModel @Inject constructor(
    private val repository: SchoolRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(TeacherUiState())
    val uiState: StateFlow<TeacherUiState> = _uiState.asStateFlow()

    fun loadDashboard() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            try {
                val classes = repository.getTeacherClasses()
                val timetable = repository.getTeacherTimetable()
                val students = repository.getTeacherStudents()
                val announcements = repository.getAnnouncements()
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    classes = classes,
                    timetable = timetable,
                    students = students,
                    announcements = announcements
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(isLoading = false, error = e.message ?: "Unable to load teacher data")
            }
        }
    }
}

data class TeacherUiState(
    val isLoading: Boolean = false,
    val classes: List<TeacherClassDto> = emptyList(),
    val timetable: List<TimetableEntryDto> = emptyList(),
    val students: List<StudentSummaryDto> = emptyList(),
    val announcements: List<AnnouncementDto> = emptyList(),
    val error: String? = null
)
