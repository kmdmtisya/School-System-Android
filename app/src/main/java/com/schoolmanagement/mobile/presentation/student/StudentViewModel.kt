package com.schoolmanagement.mobile.presentation.student

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.schoolmanagement.mobile.data.model.AnnouncementDto
import com.schoolmanagement.mobile.data.model.FeeDto
import com.schoolmanagement.mobile.data.model.ResultDto
import com.schoolmanagement.mobile.data.model.StudentProfileDto
import com.schoolmanagement.mobile.data.model.TimetableEntryDto
import com.schoolmanagement.mobile.data.repository.SchoolRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StudentViewModel @Inject constructor(
    private val repository: SchoolRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(StudentUiState())
    val uiState: StateFlow<StudentUiState> = _uiState.asStateFlow()

    fun loadDashboard() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            try {
                val profile = repository.getStudentProfile()
                val timetable = repository.getStudentTimetable()
                val results = repository.getStudentResults()
                val fees = repository.getStudentFees()
                val announcements = repository.getAnnouncements()
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    profile = profile,
                    timetable = timetable,
                    results = results,
                    fees = fees,
                    announcements = announcements
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(isLoading = false, error = e.message ?: "Unable to load student data")
            }
        }
    }
}

data class StudentUiState(
    val isLoading: Boolean = false,
    val profile: StudentProfileDto? = null,
    val timetable: List<TimetableEntryDto> = emptyList(),
    val results: List<ResultDto> = emptyList(),
    val fees: FeeDto? = null,
    val announcements: List<AnnouncementDto> = emptyList(),
    val error: String? = null
)
