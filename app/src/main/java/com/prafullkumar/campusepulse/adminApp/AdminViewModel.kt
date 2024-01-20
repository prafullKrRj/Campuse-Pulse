package com.prafullkumar.campusepulse.adminApp

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prafullkumar.campusepulse.data.AdminRepository
import com.prafullkumar.campusepulse.data.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AdminViewModel(
    private val repository: AdminRepository
): ViewModel() {


    private val _state = MutableStateFlow<AdminState>(AdminState.Initial)
    val state = _state.asStateFlow()
    fun addStudent(student: Student) {
        viewModelScope.launch {
            repository.addStudent(student)
        }
    }
    init {
        _state.value = AdminState.Loading
        getClasses()
    }
    val userData = mutableStateOf("")
    fun getUserData() {
        viewModelScope.launch {
            userData.value = repository.getUserData()
        }
    }
    private fun getClasses() {
        viewModelScope.launch {
            repository.getClasses().collect { result ->
                when(result) {
                    is Result.Loading -> {
                        _state.value = AdminState.Loading
                    }
                    is Result.Success -> {
                        _state.value = AdminState.Success(result.data)
                        Log.d("vashu", "getClasses: ${result.data}")
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
    val phone: Int = 0,
    val branch: String = "",
    val rollNo: Int = 0,
    val admissionNo: Int = 0,
    val dob: String = "",
)