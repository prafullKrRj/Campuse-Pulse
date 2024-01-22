package com.prafullkumar.campusepulse.data.adminRepos

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.prafullkumar.campusepulse.adminApp.models.Branch
import com.prafullkumar.campusepulse.adminApp.models.Student
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

interface AdminRepository {
    suspend fun addStudent(student: Student)
    suspend fun getBranches(): Flow<Result<MutableList<Branch>>>
}

@Suppress("UNCHECKED_CAST")
class AdminRepositoryImpl (
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore,
    private val context: Context
) : AdminRepository {

    /**
     *  This function adds the student to the firebase authentication and then adds the student to the firebase firestore
     * */
    override suspend fun addStudent(student: Student) {

    }
    /**
     *  This function gets the branches from the firebase firestore
     * */
    override suspend fun getBranches(): Flow<Result<MutableList<Branch>>> {
        return callbackFlow {
            try {
                trySend(Result.Loading).isSuccess
                firebaseFirestore.collection("branches")
                    .get()
                    .addOnSuccessListener { result ->
                        val branches = mutableListOf<Branch>()  // stores the branches
                        result.forEach { document ->
                            val tt = document.data["timeTable"]
                            branches.add(
                                Branch(
                                    id = document.id,
                                    name = document.data["name"].toString(),
                                    strength = document.data["strength"]?.toString()?.toInt(),
                                    tt = if (tt != null) tt as Map<String, List<String>> else null,
                                )
                            )
                        }
                        trySend(Result.Success(branches)).isSuccess // This is the line which send the data to the flow
                    }
                    .addOnFailureListener { exception ->
                        trySend(Result.Error(exception)).isSuccess
                    }
            } catch (e: Exception) {
                trySend(Result.Error(e))
            }
            awaitClose {  }
        }
    }
}
sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
    data object Loading : Result<Nothing>()
}