package com.prafullkumar.campusepulse.studentApp.notes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prafullkumar.campusepulse.data.local.TasksEntity
import com.prafullkumar.campusepulse.data.studentRepo.TasksRepository
import kotlinx.coroutines.launch

class TasksViewModel(
    private val tasksRepository: TasksRepository
): ViewModel() {

    val tasks = tasksRepository.getAllTasks()

    init {
        getAllTasks()
    }

    private fun getAllTasks() {
        viewModelScope.launch {
            tasksRepository.getAllTasks()
        }
    }

    fun addTask(task: TasksEntity) {
        viewModelScope.launch {
            tasksRepository.addTask(task)
        }
    }
}