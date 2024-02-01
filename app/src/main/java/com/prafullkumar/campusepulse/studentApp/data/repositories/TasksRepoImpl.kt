package com.prafullkumar.campusepulse.studentApp.data.repositories

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import com.prafullkumar.campusepulse.studentApp.data.local.AppDao
import com.prafullkumar.campusepulse.studentApp.data.local.TasksEntity
import com.prafullkumar.campusepulse.studentApp.domain.TasksRepository
import kotlinx.coroutines.flow.Flow


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

    override fun getCompletedTasks(): LiveData<List<TasksEntity>> {
        return appDao.getTasksByStatus(true)
    }

    override fun getIncompleteTasks(): LiveData<List<TasksEntity>> {
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