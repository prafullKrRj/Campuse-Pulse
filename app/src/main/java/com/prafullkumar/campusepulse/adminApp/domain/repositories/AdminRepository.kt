package com.prafullkumar.campusepulse.adminApp.domain.repositories

import com.prafullkumar.campusepulse.adminApp.domain.models.Branch
import com.prafullkumar.campusepulse.adminApp.domain.models.Student
import com.prafullkumar.campusepulse.model.NewBranch
import kotlinx.coroutines.flow.Flow

interface AdminRepository {
    suspend fun addStudent(student: Student, branchStrength: Long)
    suspend fun getBranches(): Flow<Result<MutableList<Branch>>>
    suspend fun addBranch(newBranch: NewBranch): Flow<Result<Boolean>>
}

sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
    data object Loading : Result<Nothing>()
}