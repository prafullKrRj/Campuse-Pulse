package com.prafullkumar.campusepulse.data.studentRepo

import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.prafullkumar.campusepulse.adminApp.models.Student
import com.prafullkumar.campusepulse.data.adminRepos.Result
import com.prafullkumar.campusepulse.managers.SharedPrefManager
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

interface StudentRepository {
    suspend fun getStudentDetails(): Flow<Result<Pair<Student, String>>>
    fun signOut()
}

@Suppress("UNCHECKED_CAST", "LABEL_NAME_CLASH")
class StudentRepositoryImpl (
    private val firestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth,
    private val sharedPrefManager: SharedPrefManager
) : StudentRepository {
    private val storage = FirebaseStorage.getInstance()

    /**
     *      This function is used to get the student details from the firestore (Branches -> Branch -> Students -> Student)
     * */
    private val main = "branches"
    private val sub = "students"
    override suspend fun getStudentDetails(): Flow<Result<Pair<Student, String>>> {
        return callbackFlow {
            trySend(Result.Loading).isSuccess
            val id = firebaseAuth.currentUser?.email?.substringBefore('@')
            var found = false
            firestore.collection(main)
                .get()
                .addOnSuccessListener { documents ->
                    for (doc in documents) {
                        if (found) continue
                        firestore.collection(main)
                            .document(doc.id)
                            .collection(sub)
                            .document(id ?: "")
                            .get()
                            .addOnSuccessListener { st ->
                                if (st.exists() && st.id == id) {
                                    storage.reference.child("timeTables/${doc.id}").downloadUrl.addOnSuccessListener {
                                        trySend(Result.Success(Pair(getStudentFromDoc(st), it.toString()))).isSuccess
                                    }.addOnFailureListener {
                                        trySend(Result.Success(Pair(getStudentFromDoc(st), ""))).isSuccess
                                    }
                                    found = true
                                }
                            }.addOnFailureListener {
                                trySend(Result.Error(it)).isSuccess
                            }
                    }
                }
                .addOnFailureListener {
                    trySend(Result.Error(it)).isSuccess
                }
            awaitClose {  }
        }
}

    override fun signOut() {
        firebaseAuth.signOut()
        sharedPrefManager.setLoggedIn(false)
        sharedPrefManager.setLoggedInUserType("")
    }

    /**
     *       This function is used to get the student details from the document snapshot
     * */
    private fun getStudentFromDoc(students: DocumentSnapshot): Student {
        return Student(
            fname = students.data?.get("fname").toString(),
            lname = students.data?.get("lname").toString(),
            rollNo = students.data?.get("rollNo").toString()?.toLong() ?: 0L,
            admNo = students.data?.get("admNo").toString()?.toLong() ?: 0L,
            branch = students.data?.get("branch")?.toString(),
            batch = students.data?.get("batch")?.toString(),
            phoneNo = students.data?.get("phoneNo")?.toString()?.toLong() ?: 0L,
            dob = students.data?.get("dob").toString(),
            attendance = students.data?.get("attendance") as Map<String, List<Long>>
        )
    }

}