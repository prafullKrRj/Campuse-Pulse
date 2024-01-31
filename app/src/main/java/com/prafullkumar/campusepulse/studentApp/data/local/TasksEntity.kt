package com.prafullkumar.campusepulse.studentApp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.StringTokenizer

@Entity(tableName = "tasks")
data class TasksEntity (
    @PrimaryKey
    val time: Long,
    val task: String,
    val isCompleted: Boolean
)