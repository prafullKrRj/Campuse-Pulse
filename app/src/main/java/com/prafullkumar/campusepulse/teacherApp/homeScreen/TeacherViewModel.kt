package com.prafullkumar.campusepulse.teacherApp.homeScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prafullkumar.campusepulse.data.adminRepos.Result
import com.prafullkumar.campusepulse.data.teacherRepos.TeacherDetails
import com.prafullkumar.campusepulse.data.teacherRepos.TeacherRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TeacherViewModel(
    private val repository: TeacherRepository,
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
            repository.getClassList().collect { repo ->
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
sealed class TeacherState {
    data object Loading : TeacherState()
    data class Success(val data: TeacherDetails) : TeacherState()
    data class Error(val error: String) : TeacherState()
}