package com.prafullkumar.campusepulse.teacherApp.attendanceScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prafullkumar.campusepulse.data.adminRepos.Result
import com.prafullkumar.campusepulse.data.teacherRepos.DisplayAttendRepository
import com.prafullkumar.campusepulse.data.teacherRepos.StudentDisplayAttendance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ShowAttendanceViewModel(
    private val repository: DisplayAttendRepository,
    private val branch: String
) : ViewModel() {

    private val _attendanceState = MutableStateFlow<AttendanceState>(AttendanceState.Loading)
    val attendanceState = _attendanceState.asStateFlow()

    init {
        getAttendance()
    }
    fun getAttendance() {
        viewModelScope.launch {
            _attendanceState.update {
                AttendanceState.Loading
            }
            repository.getAttendance(branch.substringBefore(":"), branch.substringAfter(":")).collect { repo ->
                when (repo) {
                    is Result.Loading -> {
                        _attendanceState.update {
                            AttendanceState.Loading
                        }
                    }
                    is Result.Success -> {
                        _attendanceState.update {
                            AttendanceState.Success(repo.data)
                        }
                    }
                    is Result.Error -> {
                        _attendanceState.update {
                            AttendanceState.Error(repo.exception.message.toString())
                        }
                    }
                }
            }
        }
    }
}
sealed class AttendanceState {
    data object Loading : AttendanceState()
    data class Success(val attendance: List<StudentDisplayAttendance>) : AttendanceState()
    data class Error(val error: String) : AttendanceState()
}