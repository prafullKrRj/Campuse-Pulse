package com.prafullkumar.campusepulse.data.adminRepos

import android.content.Context
import com.google.firebase.firestore.FirebaseFirestore
import com.prafullkumar.campusepulse.adminApp.models.Branch
import com.prafullkumar.campusepulse.adminApp.models.Student

interface BranchDetailsRepository {
    suspend fun getBranchDetails(): Branch
    suspend fun getStudents(): List<Student>
}

class BranchDetailsRepositoryImpl (
    private val context: Context,
    private val firestore: FirebaseFirestore
) : BranchDetailsRepository {
    override suspend fun getBranchDetails(): Branch {
        TODO("Not yet implemented")
    }

    override suspend fun getStudents(): List<Student> {
        TODO("Not yet implemented")
    }

}