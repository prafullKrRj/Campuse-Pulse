package com.prafullkumar.campusepulse.adminApp.data.repositories

import android.net.Uri
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.prafullkumar.campusepulse.adminApp.domain.models.Branch
import com.prafullkumar.campusepulse.adminApp.domain.models.Student
import com.prafullkumar.campusepulse.adminApp.domain.repositories.BranchDetailsRepository
import com.prafullkumar.campusepulse.adminApp.domain.repositories.Result
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

@Suppress("UNCHECKED_CAST")
class BranchDetailsRepositoryImpl(
    private val firestore: FirebaseFirestore
) : BranchDetailsRepository {
    private val storage = FirebaseStorage.getInstance()
    /**
     *      This function will return the branch details of the branch with the given id
     * */
    override suspend fun getBranchDetails(branchId: String): Flow<Result<Branch>> {
        return callbackFlow {
            try {
                trySend(Result.Loading)
                var tt : String? = null
                val storageRef = storage.reference      // Reference to the root of the storage
                storageRef.child("timeTables/${branchId}").downloadUrl.addOnSuccessListener { uri ->
                    tt = uri.toString() // Download the time table of the branch
                }.await()
                firestore.collection("branches")
                    .document(branchId)
                    .get()
                    .addOnSuccessListener { document ->

                        trySend(
                            Result.Success(
                                Branch(
                                    id = document.id,
                                    name = document.data?.get("name").toString(),
                                    strength = document.data?.get("strength").toString().toLong(),
                                    batches = document.data?.get("batches") as List<String> ?: listOf(),
                                    subjects = document.data?.get("subjects") as List<String> ?: listOf(),
                                    timeTable = tt
                                )
                            ))
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

            awaitClose {  }
        }
    }

    override fun updateTimeTable(branchId: String, uri: Uri): Flow<Result<Boolean>> {
        return callbackFlow {
            trySend(Result.Loading)
            val storageRef = storage.reference
            storageRef.child("timeTables/${branchId}").putFile(uri).addOnSuccessListener {
                trySend(Result.Success(true))
            }.addOnFailureListener {
                trySend(Result.Error(it))
            }
            awaitClose {  }
        }
    }
}