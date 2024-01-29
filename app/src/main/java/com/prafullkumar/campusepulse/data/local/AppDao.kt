package com.prafullkumar.campusepulse.data.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface AppDao {

    @Upsert
    suspend fun addTask(task: TasksEntity)

    @Query("DELETE FROM tasks WHERE isCompleted = :status")
    suspend fun deleteTasksByStatus(status: Boolean)

    @Query("SELECT * FROM tasks")
    fun getAllTasks(): LiveData<List<TasksEntity>>
}


