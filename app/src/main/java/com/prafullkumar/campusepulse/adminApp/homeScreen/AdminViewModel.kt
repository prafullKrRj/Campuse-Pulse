package com.prafullkumar.campusepulse.adminApp.homeScreen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prafullkumar.campusepulse.adminApp.models.Branch
import com.prafullkumar.campusepulse.adminApp.models.Student
import com.prafullkumar.campusepulse.data.adminRepos.AdminRepository
import com.prafullkumar.campusepulse.data.adminRepos.Result
import com.prafullkumar.campusepulse.model.NewBranch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AdminViewModel(
    private val repository: AdminRepository
): ViewModel() {


    private val _state = MutableStateFlow<AdminState>(AdminState.Initial)
    val state = _state.asStateFlow()

    init {
        _state.value = AdminState.Loading
        getBranches()
    }
    fun addStudent(student: Student) {
        viewModelScope.launch {
            repository.addStudent(student)
        }
    }
    private fun getBranches() {
        viewModelScope.launch {
            repository.getBranches().collect { result ->
                when(result) {
                    is Result.Loading -> {
                        _state.value = AdminState.Loading
                    }
                    is Result.Success -> {
                        _state.value = AdminState.Success(result.data)
                    }
                    is Result.Error -> {
                        _state.value = AdminState.Error(result.exception.message.toString())
                    }
                }
            }
        }
    }
    val branchName = mutableStateOf("")
    val year = mutableStateOf("")
    private val _newBranch = MutableStateFlow(NewBranch())
    val newBranch = _newBranch.asStateFlow()
    fun updateBranch(newBranch: NewBranch) {
        _newBranch.value = newBranch
    }
}
sealed class AdminState {
    data object Initial: AdminState()
    data class Success(val branches: MutableList<Branch>?): AdminState()
    data class Error(val error: String?): AdminState()
    data object Loading: AdminState()
}

