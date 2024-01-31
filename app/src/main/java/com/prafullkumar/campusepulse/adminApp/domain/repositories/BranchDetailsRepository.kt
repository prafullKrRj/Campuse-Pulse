package com.prafullkumar.campusepulse.adminApp.domain.repositories

import android.net.Uri
import com.prafullkumar.campusepulse.adminApp.domain.models.Branch
import com.prafullkumar.campusepulse.adminApp.domain.models.Student
import kotlinx.coroutines.flow.Flow

interface BranchDetailsRepository {
    suspend fun getBranchDetails(branchId: String): Flow<Result<Branch>>
    suspend fun getStudents(branchId: String): Flow<Result<List<Student>>>
    fun updateTimeTable(branchId: String, uri: Uri): Flow<Result<Boolean>>
}
