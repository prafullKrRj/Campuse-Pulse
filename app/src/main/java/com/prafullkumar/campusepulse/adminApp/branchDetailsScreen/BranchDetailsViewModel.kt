package com.prafullkumar.campusepulse.adminApp.branchDetailsScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prafullkumar.campusepulse.adminApp.models.Branch
import com.prafullkumar.campusepulse.adminApp.models.Student
import com.prafullkumar.campusepulse.data.adminRepos.BranchDetailsRepository
import com.prafullkumar.campusepulse.data.adminRepos.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BranchDetailsViewModel(
    private val repository: BranchDetailsRepository,
    private val id: String
) : ViewModel() {

    private val _branchDetails = MutableStateFlow<BranchDetailsState>(BranchDetailsState.Initial)
    val branchDetails = _branchDetails.asStateFlow()

    private val _students = MutableStateFlow<StudentsState>(StudentsState.Initial)
    val studentsFlow = _students.asStateFlow()
    init {
        getBranchDetails()
        getStudents()
    }
    private fun getBranchDetails() {
        viewModelScope.launch {
            repository.getBranchDetails(id).collect { repo ->
                when (repo) {
                    is Result.Error -> {
                        _branchDetails.update {
                            BranchDetailsState.Error(repo.exception.message.toString())
                        }
                    }
                    is Result.Loading -> {
                        _branchDetails.update {
                            BranchDetailsState.Loading
                        }
                    }
                    is Result.Success -> {
                        _branchDetails.update {
                            BranchDetailsState.Success(repo.data)
                        }
                    }
                }
            }
        }
    }
    private fun getStudents() {
        viewModelScope.launch {
            repository.getStudents(id).collect { repo ->
                when (repo) {
                    is Result.Error -> {
                        _students.update {
                            StudentsState.Error(repo.exception.message.toString())
                        }
                    }
                    is Result.Loading -> {
                        _students.update {
                            StudentsState.Loading
                        }
                    }
                    is Result.Success -> {
                        _students.update {
                            StudentsState.Success(repo.data)
                        }
                    }
                }
            }
        }
    }
}
sealed class BranchDetailsState {
    data object Initial: BranchDetailsState()
    data object Loading: BranchDetailsState()
    data class Success(val branchDetails: Branch) : BranchDetailsState()
    data class Error(val error: String?): BranchDetailsState()
}
sealed class StudentsState {
    data object Initial: StudentsState()
    data object Loading: StudentsState()
    data class Success(val studentList: List<Student>) : StudentsState()
    data class Error(val error: String?): StudentsState()
}