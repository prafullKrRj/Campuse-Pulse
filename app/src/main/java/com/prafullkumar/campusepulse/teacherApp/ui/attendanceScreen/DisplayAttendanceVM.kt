package com.prafullkumar.campusepulse.teacherApp.ui.attendanceScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prafullkumar.campusepulse.adminApp.domain.repositories.Result
import com.prafullkumar.campusepulse.teacherApp.domain.repositories.DisplayAttendRepository
import com.prafullkumar.campusepulse.teacherApp.ui.homeScreen.TeacherState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DisplayAttendanceViewModel(
    private val repository: DisplayAttendRepository
) : ViewModel() {

    private val _teacherState = MutableStateFlow<TeacherState>(TeacherState.Loading)
    val teacherState = _teacherState.asStateFlow()


    init {
        getClassList()
    }
    fun getClassList() {
        _teacherState.update {
            TeacherState.Loading
        }
        viewModelScope.launch {
            repository.getClasses().collect { repo ->
                when (repo) {
                    is Result.Loading -> {
                        _teacherState.update {
                            TeacherState.Loading
                        }
                    }
                    is Result.Success -> {
                        _teacherState.update {
                            TeacherState.Success(repo.data)
                        }
                    }

                    is Result.Error -> {
                        _teacherState.update {
                            TeacherState.Error(repo.exception.message.toString())
                        }
                    }
                }
            }
        }
    }


}