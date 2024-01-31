package com.prafullkumar.campusepulse.teacherApp.domain.repositories

import com.prafullkumar.campusepulse.adminApp.domain.repositories.Result
import kotlinx.coroutines.flow.Flow

interface TeacherRepository {
    suspend fun getClassList(): Flow<Result<TeacherDetails>>
}

data class TeacherDetails(
    val id: String?,
    val name: String?,
    val branches: List<String>?
)
