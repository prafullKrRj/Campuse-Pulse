package com.prafullkumar.campusepulse.data

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.prafullkumar.campusepulse.adminApp.Branch
import com.prafullkumar.campusepulse.adminApp.Student
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

interface AdminRepository {
    suspend fun getUserData(): String
    suspend fun addStudent(student: Student)
    suspend fun getClasses(): Flow<Result<MutableList<Branch>>>
}

class AdminRepositoryImpl (
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore,
    private val context: Context
) : AdminRepository {
    override suspend fun getUserData(): String {
        firebaseAuth.currentUser?.let {
            return it.toString()
        }
        return "Hello World"
    }
    override suspend fun addStudent(student: Student) {
        firebaseAuth.createUserWithEmailAndPassword("${student.admissionNo}@student.com", "password")
            .addOnSuccessListener {
                addToStudentDatabase(student)
            }
            .addOnFailureListener {
                Toast.makeText(context, "${it.message}", Toast.LENGTH_SHORT).show()
            }
    }
    private fun addToStudentDatabase(student: Student) {
        val user = hashMapOf(
            "fName" to student.fName,
            "lName" to student.lName,
            "dob" to student.dob,
            "rollNo" to student.rollNo,
            "admissionNo" to student.admissionNo,
            "branch" to listOf(student.branch),
            "phoneNo" to student.phone,
            "batch" to student.batch
        )
        firebaseFirestore.collection("students")
            .add(user)
            .addOnSuccessListener { documentReference ->
                Toast.makeText(context, "Added Successfully", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Toast.makeText(context, "Error adding student", Toast.LENGTH_SHORT).show()
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