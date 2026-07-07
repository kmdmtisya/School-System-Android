package com.schoolmanagement.mobile.presentation.parent

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.schoolmanagement.mobile.data.model.AnnouncementDto
import com.schoolmanagement.mobile.data.model.ChildDto
import com.schoolmanagement.mobile.data.model.FeeDto
import com.schoolmanagement.mobile.data.model.ResultDto
import com.schoolmanagement.mobile.data.repository.SchoolRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ParentViewModel @Inject constructor(
    private val repository: SchoolRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ParentUiState())
    val uiState: StateFlow<ParentUiState> = _uiState.asStateFlow()

    fun loadDashboard() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            try {
                val children = repository.getChildren()
                val announcements = repository.getAnnouncements()
                val firstChild = children.firstOrNull()
                val results = firstChild?.let { repository.getChildResults(it.id) } ?: emptyList()
                val fees = firstChild?.let { repository.getChildFees(it.id) } ?: FeeDto()
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    children = children,
                    results = results,
                    fees = fees,
                    announcements = announcements
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(isLoading = false, error = e.message ?: "Unable to load parent data")
            }
        }
    }
}

data class ParentUiState(
    val isLoading: Boolean = false,
    val children: List<ChildDto> = emptyList(),
    val results: List<ResultDto> = emptyList(),
    val fees: FeeDto? = null,
    val announcements: List<AnnouncementDto> = emptyList(),
    val error: String? = null
)
