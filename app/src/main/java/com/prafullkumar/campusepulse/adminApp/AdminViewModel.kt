package com.prafullkumar.campusepulse.adminApp

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prafullkumar.campusepulse.data.AdminRepository
import kotlinx.coroutines.launch

class AdminViewModel(
    private val repository: AdminRepository
): ViewModel() {

    var userData = mutableStateOf("")
    init {
        viewModelScope.launch {
            userData.value = repository.getUserData()
        }
    }
}