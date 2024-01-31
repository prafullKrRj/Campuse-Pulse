package com.prafullkumar.campusepulse.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import com.prafullkumar.campusepulse.data.local.TasksEntity
@Dao
interface AppDao {

    @Upsert
    suspend fun addTask(task: TasksEntity)

    @Query("DELETE FROM tasks WHERE isCompleted = :status")
    suspend fun deleteTasksByStatus(status: Boolean)

    @Query("SELECT * FROM tasks WHERE isCompleted = :status ORDER BY time DESC")
    fun getTasksByStatus(status: Boolean): Flow<List<TasksEntity>>


    @Delete
    suspend fun deleteTask(task: TasksEntity)

    @Query("DELETE FROM tasks")
    suspend fun deleteAllTasks()

}
