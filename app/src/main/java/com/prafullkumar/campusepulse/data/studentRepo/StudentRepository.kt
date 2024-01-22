package com.prafullkumar.campusepulse.data.studentRepo

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.prafullkumar.campusepulse.adminApp.models.Student
import com.prafullkumar.campusepulse.data.adminRepos.Result
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

interface StudentRepository {
    suspend fun getStudentDetails(): Flow<Result<Student>>
}

@Suppress("UNCHECKED_CAST", "LABEL_NAME_CLASH")
class StudentRepositoryImpl (
    private val firestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth
) : StudentRepository {
    override suspend fun getStudentDetails(): Flow<Result<Student>>  {
        return callbackFlow {
            trySend(Result.Loading)
            val id = firebaseAuth.currentUser?.email?.substringBefore('@')
            val docRef = firestore.collection("branches").get()
                .addOnSuccessListener { documents ->
                    for (docs in documents) {
                        val branch = docs.id
                        val docRef = firestore.collection("branches").document(branch).collection("students").document(id!!)
                        docRef.get()
                            .addOnSuccessListener { students ->
                                if (docs != null) {
                                    trySend(Result.Success(
                                        getStudentFromDoc(students)
                                    ))
                                    return@addOnSuccessListener
                                }
                            }
                    }
                }
                .addOnFailureListener { error ->
                    trySend(Result.Error(error))
                }
            awaitClose {  }
        }
    }
    private fun getStudentFromDoc(students: DocumentSnapshot): Student {
        return Student(
            fName = students.data?.get("fname").toString(),
            lName = students.data?.get("lname").toString(),
            rollNo = students.data?.get("rollNo").toString().toLong(),
            admissionNo = students.data?.get("admNo").toString().toLong(),
            branch = students.data?.get("branch").toString(),
            batch = students.data?.get("batch").toString(),
            phone = students.data?.get("phoneNo").toString().toLong(),
            dob = students.data?.get("dob").toString(),
            attendance = students.data?.get("attendance") as Map<String, List<String>>
        )
    }
}