package com.prafullkumar.campusepulse.data.studentRepo

import android.content.Context
import android.widget.Toast
import com.prafullkumar.campusepulse.data.local.AppDao
import com.prafullkumar.campusepulse.data.local.TasksEntity
import kotlinx.coroutines.flow.Flow

interface TasksRepository {
    suspend fun addTask(task: TasksEntity)
    suspend fun deleteByStatus(status: Boolean)
    fun getCompletedTasks(): Flow<List<TasksEntity>>
    fun getIncompleteTasks(): Flow<List<TasksEntity>>
    suspend fun deleteTask(task: TasksEntity)
    suspend fun deleteAll()
}
class TasksRepositoryImpl (
    private val appDao: AppDao,
    private val context: Context
) : TasksRepository {
    override suspend fun addTask(task: TasksEntity) {
        try {
            appDao.addTask(task)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun deleteByStatus(status: Boolean) {
        appDao.deleteTasksByStatus(status)
    }

    override fun getCompletedTasks(): Flow<List<TasksEntity>> {
        return appDao.getTasksByStatus(true)
    }

    override fun getIncompleteTasks(): Flow<List<TasksEntity>> {
        return appDao.getTasksByStatus(false)
    }

    override suspend fun deleteTask(task: TasksEntity) {
        try {
            appDao.deleteTask(task)
            Toast.makeText(context, "Task deleted", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend  fun deleteAll() {
        appDao.deleteAllTasks()
    }
}