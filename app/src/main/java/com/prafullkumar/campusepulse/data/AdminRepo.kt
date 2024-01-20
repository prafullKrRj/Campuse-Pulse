package com.prafullkumar.campusepulse.data

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.prafullkumar.campusepulse.adminApp.Branch
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

interface AdminRepository {
    suspend fun getUserData(): String
    fun addStudent()
    suspend fun getClasses(): Flow<Result<MutableList<Branch>>>
}

class AdminRepositoryImpl (
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore
) : AdminRepository {
    override suspend fun getUserData(): String {
        firebaseAuth.currentUser?.let {
            return it.toString()
        }
        return "Hello World"
    }
    override fun addStudent() {
        val user = hashMapOf(
            "fName" to "Vashu",
            "lName" to "Solanki",
            "dob" to "09/11/2004",
            "rollNo" to 1233,
            "admissionNo" to 22999,
            "branch" to listOf("se", "entc", "a"),
            "phoneNo" to 1234567890,
        )
        firebaseFirestore.collection("students")
            .add(user)
            .addOnSuccessListener { documentReference ->
                Log.d("vashu", "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w("vashu", "Error adding document", e)
            }
    }
    override suspend fun getClasses(): Flow<Result<MutableList<Branch>>> {
        return callbackFlow {
            trySend(Result.Loading).isSuccess
            val subscription = firebaseFirestore.collection("branches")
                .get()
                .addOnSuccessListener { result ->
                    val list = mutableListOf<Branch>()
                    for (document in result) {
                        val branch = Branch(
                            name = document.data["name"].toString(),
                            total = document.data["total"].toString().toInt(),
                            tt = document.data["tt"].toString()
                        )
                        list.add(branch)
                    }
                    trySend(Result.Success(list)).isSuccess
                }
                .addOnFailureListener { exception ->
                    Log.w("vashu", "Error getting documents.", exception)
                    trySend(Result.Error(exception)).isSuccess
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