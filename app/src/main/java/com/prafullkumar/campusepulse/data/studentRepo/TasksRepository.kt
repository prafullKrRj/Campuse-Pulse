package com.prafullkumar.campusepulse.data.studentRepo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.prafullkumar.campusepulse.data.local.AppDao
import com.prafullkumar.campusepulse.data.local.TasksEntity

interface TasksRepository {
    suspend fun addTask(task: TasksEntity)
    suspend fun deleteCompletedTasks()
    fun getAllTasks(): LiveData<List<TasksEntity>>
}
class TasksRepositoryImpl (
    private val appDao: AppDao
) : TasksRepository {
    override suspend fun addTask(task: TasksEntity) {
        appDao.addTask(task)
    }

    override suspend fun deleteCompletedTasks() {
        appDao.deleteTasksByStatus(true)
    }

    override fun getAllTasks(): LiveData<List<TasksEntity>> {
        return appDao.getAllTasks()
    }
}