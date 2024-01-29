package com.prafullkumar.campusepulse.adminApp.addBranchScreen

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prafullkumar.campusepulse.data.adminRepos.AdminRepository
import com.prafullkumar.campusepulse.data.adminRepos.Result
import com.prafullkumar.campusepulse.data.teacherRepos.TeacherDetails
import com.prafullkumar.campusepulse.model.ClassDetails
import com.prafullkumar.campusepulse.model.NewBranch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AddBranchViewModel(
    private val repository: AdminRepository
): ViewModel() {
    private val _teachers: MutableStateFlow<TeacherDetailsState> = MutableStateFlow(TeacherDetailsState.Loading)
    val teachers = _teachers.asStateFlow()

    val state:MutableStateFlow<AddBranchState> = MutableStateFlow(AddBranchState())
    init {
        getTeachers()
    }
    private fun getTeachers() {
        _teachers.value = TeacherDetailsState.Loading
        viewModelScope.launch {
            repository.getTeachers().collect { repo ->
                when (repo) {
                    Result.Loading -> {
                        _teachers.update {
                            TeacherDetailsState.Loading
                        }
                    }
                    is Result.Success -> {
                        _teachers.update {
                            TeacherDetailsState.Success(repo.data)
                        }
                        Log.d("teacher", "getTeachers: ${repo.data}")
                    }
                    is Result.Error -> {
                        _teachers.update {
                            TeacherDetailsState.Error(repo.exception.message ?: "Error")
                        }
                    }
                }
            }
        }
    }
    fun addBranch() {
        viewModelScope.launch {
            repository.addBranch(state.value.newBranch)
        }
    }
    fun resetNewBranch() {
        state.update {
            AddBranchState()
        }
    }
}

data class AddBranchState(
    var newBranch: NewBranch = NewBranch(),
    var slot: ClassDetails = ClassDetails()
)

sealed class TeacherDetailsState {
    data object Loading: TeacherDetailsState()
    data class Success(val data: MutableList<TeacherDetails>): TeacherDetailsState()
    data class Error(val message: String): TeacherDetailsState()
}