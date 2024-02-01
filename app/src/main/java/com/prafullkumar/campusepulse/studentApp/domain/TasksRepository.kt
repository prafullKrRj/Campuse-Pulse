package com.prafullkumar.campusepulse.studentApp.domain

import androidx.lifecycle.LiveData
import com.prafullkumar.campusepulse.studentApp.data.local.TasksEntity

interface TasksRepository {
    suspend fun addTask(task: TasksEntity)
    suspend fun deleteByStatus(status: Boolean)
    fun getCompletedTasks(): LiveData<List<TasksEntity>>
    fun getIncompleteTasks(): LiveData<List<TasksEntity>>
    suspend fun deleteTask(task: TasksEntity)
    suspend fun deleteAll()
}