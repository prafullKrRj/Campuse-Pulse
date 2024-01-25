package com.prafullkumar.campusepulse.teacherApp.homeScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prafullkumar.campusepulse.data.teacherRepos.TeacherRepository
import kotlinx.coroutines.launch

class TeacherViewModel(
    private val repository: TeacherRepository,
) : ViewModel() {


    init {

    }
    fun getClassList() {
        viewModelScope.launch {

        }
    }
}
