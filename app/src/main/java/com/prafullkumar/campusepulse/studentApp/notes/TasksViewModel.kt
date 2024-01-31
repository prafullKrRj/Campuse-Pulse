package com.prafullkumar.campusepulse.studentApp.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.prafullkumar.campusepulse.data.local.TasksEntity
import com.prafullkumar.campusepulse.data.studentRepo.TasksRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TasksViewModel(
    private val tasksRepository: TasksRepository
): ViewModel() {

    private var _completedTasks: StateFlow<List<TasksEntity>> = tasksRepository.getCompletedTasks().stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList()
    )
    val completedTasks: StateFlow<List<TasksEntity>> = _completedTasks

    private var _incompleteTasks: StateFlow<List<TasksEntity>> = tasksRepository.getIncompleteTasks().stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList()
    )
    val incompleteTasks: StateFlow<List<TasksEntity>> = _incompleteTasks


    fun addTask(task: TasksEntity) {
        viewModelScope.launch {
            tasksRepository.addTask(task)
        }
    }

    fun deleteTask(task: TasksEntity) {
        viewModelScope.launch {
            tasksRepository.deleteTask(task)
        }
    }

    fun deleteAll() {
        viewModelScope.launch {
            tasksRepository.deleteAll()
        }
    }

    fun deleteCompleted() {
        viewModelScope.launch {
            tasksRepository.deleteByStatus(true)
        }
    }

    fun deleteInCompleted() {
        viewModelScope.launch {
            tasksRepository.deleteByStatus(false)
        }
    }
}