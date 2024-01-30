package com.prafullkumar.campusepulse.data.teacherRepos

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.prafullkumar.campusepulse.adminApp.models.Student
import com.prafullkumar.campusepulse.data.adminRepos.Result
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

interface DisplayAttendRepository {
    suspend fun getClasses(): Flow<Result<TeacherDetails>>
    suspend fun getAttendance(branch: String, subject: String): Flow<Result<List<StudentDisplayAttendance>>>
}

class DisplayAttendRepositoryImpl (
    private val firestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth
) : DisplayAttendRepository {

    val id: String = firebaseAuth.currentUser?.email?.substringBefore('@') ?: ""
    override suspend fun getClasses(): Flow<Result<TeacherDetails>> {
        return callbackFlow {
            trySend(Result.Loading)
            if (id == "") {
                trySend(Result.Error(Exception("User not logged in")))
                return@callbackFlow
            }
            val docRef = firestore.collection("teachers")
                .document(id)
                .get()
                .addOnSuccessListener {
                    val teacherDetails = TeacherDetails(
                        id = it.id,
                        name = it.data?.get("name") as String?,
                        branches = it.data?.get("branches") as List<String>?
                    )
                    trySend(Result.Success(teacherDetails))
                }
                .addOnFailureListener {
                    trySend(Result.Error(it))
                }
            awaitClose { docRef.isComplete }
        }
    }

    override suspend fun getAttendance(
        branch: String,
        subject: String
    ): Flow<Result<List<StudentDisplayAttendance>>> {
        return callbackFlow {
            trySend(Result.Loading)
            firestore.collection("branches").document(branch).collection("students").get()
                .addOnFailureListener {
                    trySend(Result.Error(it))
                }
                .addOnSuccessListener { documents ->
                    val studentList = mutableListOf<StudentDisplayAttendance>()
                    for (document in documents) {
                        val s = getStudentFromDoc(document)
                        val student = StudentDisplayAttendance(
                            name = s.fname + " " + s.lname,
                            rollNo = s.rollNo.toString(),
                            present = s.attendance?.get(subject)?.get(0),
                            absent = s.attendance?.get(subject)?.get(1)
                        )
                        studentList.add(student)
                    }
                    trySend(Result.Success(studentList))
                }

            awaitClose { }
        }
    }
    private fun getStudentFromDoc(students: DocumentSnapshot): Student {
        return Student(
            fname = students.data?.get("fname").toString(),
            lname = students.data?.get("lname").toString(),
            rollNo = students.data?.get("rollNo").toString().toLong(),
            admNo = students.data?.get("admNo").toString().toLong(),
            branch = students.data?.get("branch").toString(),
            batch = students.data?.get("batch").toString(),
            phoneNo = students.data?.get("phoneNo").toString().toLong(),
            dob = students.data?.get("dob").toString(),
            attendance = students.data?.get("attendance") as Map<String, List<Long>>
        )
    }
}
data class StudentDisplayAttendance(
    val name: String?,
    val rollNo: String?,
    val present: Long?,
    val absent: Long?,
)