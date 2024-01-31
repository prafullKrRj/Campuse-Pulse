package com.prafullkumar.campusepulse.studentApp.homeScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prafullkumar.campusepulse.adminApp.models.Student
import com.prafullkumar.campusepulse.data.adminRepos.Result
import com.prafullkumar.campusepulse.data.studentRepo.StudentRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
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
    fun getStudentDetails() {
        viewModelScope.launch {
            repository.getStudentDetails().collect { repo ->
                when (repo) {
                    is Result.Error ->  {
                        _studentScreenState.update {
                            StudentScreenState.Error(repo.exception.message ?: "Error")
                        }
                    }
                    Result.Loading ->  {
                        _studentScreenState.update {
                            StudentScreenState.Loading
                        }
                    }
                    is Result.Success -> {
                        _studentScreenState.update {
                            StudentScreenState.Success(repo.data)
                        }
                    }
                }
            }
        }
    }

    fun clearDatabase() {
        viewModelScope.launch {
            repository.clearDatabase()
        }
    }
}

sealed class StudentScreenState {
    data object Initial: StudentScreenState()
    data object Loading: StudentScreenState()
    data class Success(val studentData: Pair<Student, String>) : StudentScreenState()
    data class Error(val error: String?): StudentScreenState()
}