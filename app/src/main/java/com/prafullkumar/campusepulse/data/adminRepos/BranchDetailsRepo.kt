package com.prafullkumar.campusepulse.data.adminRepos

import com.google.firebase.firestore.FirebaseFirestore
import com.prafullkumar.campusepulse.adminApp.models.Branch
import com.prafullkumar.campusepulse.adminApp.models.Student
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

interface BranchDetailsRepository {
    suspend fun getBranchDetails(branchId: String): Flow<Result<Branch>>
    suspend fun getStudents(branchId: String): Flow<Result<List<Student>>>
}

@Suppress("UNCHECKED_CAST")
class BranchDetailsRepositoryImpl(
    private val firestore: FirebaseFirestore
) : BranchDetailsRepository {

    /**
     *      This function will return the branch details of the branch with the given id
     * */
    override suspend fun getBranchDetails(branchId: String): Flow<Result<Branch>> {
        return callbackFlow {
            try {
                trySend(Result.Loading)
                firestore.collection("branches")
                    .document(branchId)
                    .get()
                    .addOnSuccessListener { document ->
                        val tt = document.data?.get("timeTable")
                        trySend(Result.Success(
                            Branch(
                                id = document.id,
                                name = document.data?.get("name").toString(),
                                strength = document.data?.get("strength")?.toString()?.toInt(),
                                tt = if (tt != null) tt as Map<String, List<String>> else null,
                                batches = document.data?.get("batches") as List<String>,
                                subjects = document.data?.get("subjects") as List<String>
                            )
                        )
                        )
                    }
                    .addOnFailureListener { error ->
                        trySend(Result.Error(error))
                    }
            } catch (e: Exception) {
                trySend(Result.Error(e))
            }

            awaitClose {  }
        }
    }

    /**
     *      This function will return the list of students of the branch with the given id
     * */
    override suspend fun getStudents(branchId: String): Flow<Result<List<Student>>> {
        return callbackFlow {
            try {
                trySend(Result.Loading)
                firestore.collection("branches").document(branchId)
                    .collection("students")
                    .get()
                    .addOnSuccessListener {
                        val students = mutableListOf<Student>()
                        for (document in it.documents) {
                            students.add(
                                Student(
                                    fname = document.data?.get("fname").toString(),
                                    lname = document.data?.get("lname").toString(),
                                    rollNo = document.data?.get("rollNo").toString().toLong(),
                                    admNo = document.data?.get("admNo").toString().toLong(),
                                    branch = document.data?.get("branch").toString(),
                                    batch = document.data?.get("batch").toString(),
                                    phoneNo = document.data?.get("phoneNo").toString().toLong(),
                                    dob = document.data?.get("dob").toString(),
                                    attendance = document.data?.get("attendance") as Map<String, List<Long>>
                                )
                            )
                        }
                        trySend(Result.Success(students))
                    }
                    .addOnFailureListener {
                        trySend(Result.Error(it))
                    }
            } catch (e: Exception) {
                trySend(Result.Error(e))
            }
            awaitClose {  }
        }
    }
}