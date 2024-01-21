package com.prafullkumar.campusepulse.adminApp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prafullkumar.campusepulse.data.AdminRepository
import com.prafullkumar.campusepulse.data.Result
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
}
sealed class AdminState {
    data object Initial: AdminState()
    data class Success(val branches: MutableList<Branch>): AdminState()
    data class Error(val error: String): AdminState()
    data object Loading: AdminState()
}

data class Branch(
    val name: String = "",
    val total: Int = 0,
    val tt: String = ""
)
data class Student(
    val fName: String = "",
    val lName: String = "",
    val phone: Long = 0,
    val branch: String = "",
    val rollNo: Long = 0,
    val admissionNo: Long = 0,
    val dob: String = "",
    val batch: String = ""
)