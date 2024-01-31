package com.prafullkumar.campusepulse.teacherApp.domain.repositories

import com.prafullkumar.campusepulse.adminApp.domain.repositories.Result
import kotlinx.coroutines.flow.Flow

interface DisplayAttendRepository {
    suspend fun getClasses(): Flow<Result<TeacherDetails>>
    suspend fun getAttendance(branch: String, subject: String): Flow<Result<List<StudentDisplayAttendance>>>
}

data class StudentDisplayAttendance(
    val name: String?,
    val rollNo: String?,
    val present: Long?,
    val absent: Long?,
)