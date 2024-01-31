package com.prafullkumar.campusepulse.studentApp.domain

import com.prafullkumar.campusepulse.adminApp.domain.models.Student
import com.prafullkumar.campusepulse.adminApp.domain.repositories.Result
import kotlinx.coroutines.flow.Flow

interface StudentRepository {
    suspend fun getStudentDetails(): Flow<Result<Pair<Student, String>>>
    suspend fun clearDatabase()
}
