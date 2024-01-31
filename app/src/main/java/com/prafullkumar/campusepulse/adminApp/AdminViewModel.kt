package com.prafullkumar.campusepulse.adminApp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prafullkumar.campusepulse.adminApp.domain.models.Branch
import com.prafullkumar.campusepulse.adminApp.domain.models.Student
import com.prafullkumar.campusepulse.adminApp.domain.repositories.AdminRepository
import com.prafullkumar.campusepulse.adminApp.domain.repositories.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AdminViewModel(
    private val repository: AdminRepository
): ViewModel() {
    private val _state = MutableStateFlow<AdminState>(AdminState.Initial)
    val state = _state.asStateFlow()
    val branches = mutableStateOf<MutableList<Branch>?>(null)
    var newStudent by mutableStateOf(NewStudent())
    var newTeacher by mutableStateOf(NewTeacher())
    init {
        _state.value = AdminState.Loading
        getBranches()
    }
    fun addStudent() {
        viewModelScope.launch {
            repository.addStudent(
                Student(
                    fname = newStudent.fName,
                    lname = newStudent.lName,
                    phoneNo = newStudent.phone?.toLong(),
                    branch = newStudent.branch,
                    rollNo = newStudent.rollNo?.toLong(),
                    admNo = newStudent.admissionNo?.toLong(),
                    dob = newStudent.dob,
                    batch = newStudent.batch,
                    attendance = createMap(
                        branches.value?.find { it.name == newStudent.branch }?.subjects ?: listOf()
                    )
                ),
                branches.value?.find { it.name == newStudent.branch }?.strength ?: 0L
            )
        }
    }
    private fun createMap(strings: List<String>): Map<String, List<Long>> {
        val map = mutableMapOf<String, List<Long>>()
        strings.forEach { string ->
            map[string] = listOf(0L, 0L)
        }
    return map
}
    fun getBranches() {
        viewModelScope.launch {
            repository.getBranches().collect { result ->
                when(result) {
                    is Result.Loading -> {
                        _state.update {
                            AdminState.Loading
                        }
                    }
                    is Result.Success -> {
                        _state.update {
                            AdminState.Success(result.data)
                        }
                        branches.value = result.data
                    }
                    is Result.Error -> {
                        _state.update {
                            AdminState.Error(result.exception.message)
                        }
                    }
                }
            }
        }
    }
    fun resetStudent() {
        newStudent = NewStudent()
    }
    fun resetTeacher() {
        newTeacher = NewTeacher()
    }
}

data class NewTeacher(
    val name: String = "",
    val branches: List<TeacherBranch> = emptyList()
)
data class TeacherBranch(
    val year: String,
    val branch: String,
    val subject: String
)
data class NewStudent(
    val fName: String? = "",
    val lName: String? = "",
    val phone: String? = "",
    val branch: String? = "",
    val rollNo: String? = "",
    val admissionNo: String?= "",
    val dob: String? = "",
    val batch: String? = "",
)
sealed class AdminState {
    data object Initial: AdminState()
    data class Success(val branches: MutableList<Branch>?): AdminState()
    data class Error(val error: String?): AdminState()
    data object Loading: AdminState()
}

