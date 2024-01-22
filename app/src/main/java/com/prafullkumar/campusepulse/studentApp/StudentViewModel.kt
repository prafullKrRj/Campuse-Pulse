package com.prafullkumar.campusepulse.studentApp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prafullkumar.campusepulse.adminApp.models.Student
import com.prafullkumar.campusepulse.data.adminRepos.Result
import com.prafullkumar.campusepulse.data.studentRepo.StudentRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class StudentViewModel(
    private val repository: StudentRepository
) : ViewModel() {

    private val _studentScreenState = MutableStateFlow<StudentScreenState>(StudentScreenState.Initial)
    val studentScreenState = _studentScreenState.asStateFlow()
    init {
        getStudentDetails()
    }
    private fun getStudentDetails() {
        viewModelScope.launch {
            repository.getStudentDetails().collect { repo ->
                when(repo) {
                    is Result.Loading -> {
                        _studentScreenState.update { StudentScreenState.Loading }
                    }
                    is Result.Success -> {
                        _studentScreenState.update { StudentScreenState.Success(repo.data) }
                    }
                    is Result.Error -> {
                        _studentScreenState.update { StudentScreenState.Error(repo.exception.message) }
                    }
                }
            }
        }
    }
}

sealed class StudentScreenState {
    data object Initial: StudentScreenState()
    data object Loading: StudentScreenState()
    data class Success(val studentData: Pair<Student, Map<String, List<String>>>) : StudentScreenState()
    data class Error(val error: String?): StudentScreenState()
}