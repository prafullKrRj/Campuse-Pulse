package com.prafullkumar.campusepulse.studentApp.domain

import com.prafullkumar.campusepulse.studentApp.data.local.TasksEntity
import kotlinx.coroutines.flow.Flow

interface TasksRepository {
    suspend fun addTask(task: TasksEntity)
    suspend fun deleteByStatus(status: Boolean)
    fun getCompletedTasks(): Flow<List<TasksEntity>>
    fun getIncompleteTasks(): Flow<List<TasksEntity>>
    suspend fun deleteTask(task: TasksEntity)
    suspend fun deleteAll()
}