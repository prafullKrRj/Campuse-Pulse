package com.prafullkumar.campusepulse.studentApp.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDao {

    @Upsert
    suspend fun addTask(task: TasksEntity)

    @Query("DELETE FROM tasks WHERE isCompleted = :status")
    suspend fun deleteTasksByStatus(status: Boolean)

    @Query("SELECT * FROM tasks WHERE isCompleted = :status ORDER BY time DESC")
    fun getTasksByStatus(status: Boolean): LiveData<List<TasksEntity>>


    @Delete
    suspend fun deleteTask(task: TasksEntity)

    @Query("DELETE FROM tasks")
    suspend fun deleteAllTasks()

}
