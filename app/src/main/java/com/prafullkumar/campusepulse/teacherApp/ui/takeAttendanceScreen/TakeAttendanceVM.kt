package com.prafullkumar.campusepulse.teacherApp.ui.takeAttendanceScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prafullkumar.campusepulse.adminApp.domain.models.Student
import com.prafullkumar.campusepulse.adminApp.domain.repositories.Result
import com.prafullkumar.campusepulse.teacherApp.domain.repositories.AttendanceRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AttendanceViewModel(
    private val repository: AttendanceRepository,
    private val branch: String,
) : ViewModel() {

    private val _takeAttendanceState = MutableStateFlow<TakeAttendanceState>(TakeAttendanceState.Loading)
    val state = _takeAttendanceState.asStateFlow()
    var attendanceList = mutableListOf<Student>()
    private var studentList = mutableListOf<Student>()          // all students list
    init {
        getClassStudentList()
    }
    fun getClassStudentList() {
        viewModelScope.launch {
            _takeAttendanceState.update {
                TakeAttendanceState.Loading
            }
            repository.getClassStudentList(branch.substringBefore(":")).collect { repo ->
                when (repo) {
                    is Result.Success -> {
                        _takeAttendanceState.update {
                            TakeAttendanceState.Success(repo.data)
                        }
                        studentList = repo.data.toMutableList()
                    }
                    is Result.Error -> {
                        _takeAttendanceState.update {
                            TakeAttendanceState.Error(repo.exception.message.toString())
                        }
                    }
                    is Result.Loading -> {
                        _takeAttendanceState.update {
                            TakeAttendanceState.Loading
                        }
                    }
                }
            }
        }
    }

    fun subTractAttendance(studentID: Long?) {
        viewModelScope.launch {
            repository.subTractAttendance(
                branch = branch.substringBefore(":"),
                studentID = studentID.toString(),
                subject = branch.substringAfter(":")
            )
        }
    }
    fun addAttendance(studentID: Long?) {
        viewModelScope.launch {
            repository.addAttendance(
                branch = branch.substringBefore(":"),
                studentID = studentID.toString(),
                subject = branch.substringAfter(":")
            )
        }
    }

    fun onSavedClicked() {
        val list = mutableListOf<String>()
        for (student in studentList) {
            if (!attendanceList.contains(student)) {
                list.add(student.admNo.toString())
            }
        }
        viewModelScope.launch {
            repository.addFinalAttendance(
                branch = branch.substringBefore(":"),
                subject = branch.substringAfter(":"),
                students = list
            )
        }
    }
}

sealed class TakeAttendanceState {
    data object Loading: TakeAttendanceState()
    data class Success(val data: List<Student>) : TakeAttendanceState()
    data class Error(val error: String) : TakeAttendanceState()
}