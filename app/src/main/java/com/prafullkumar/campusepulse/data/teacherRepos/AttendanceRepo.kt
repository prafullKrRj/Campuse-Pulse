package com.prafullkumar.campusepulse.data.teacherRepos

import android.content.Context
import android.widget.Toast
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.prafullkumar.campusepulse.adminApp.models.Student
import com.prafullkumar.campusepulse.data.adminRepos.Result
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

interface AttendanceRepository {
    suspend fun getClassStudentList(branch: String): Flow<Result<List<Student>>>
    suspend fun subTractAttendance(branch: String, studentID: String, subject: String)
    suspend fun addAttendance(branch: String, studentID: String, subject: String)
}

class AttendanceRepositoryImpl(
    private val firebaseFirestore: FirebaseFirestore,
    private val context: Context
) : AttendanceRepository {

    override suspend fun getClassStudentList(branch: String): Flow<Result<List<Student>>> {
         return callbackFlow {
             trySend(Result.Loading)
             val studentList = mutableListOf<Student>()
             val docRef = firebaseFirestore.collection("branches").document(branch).collection("students").get().addOnSuccessListener {
                 for (document in it.documents) {
                     val student = getStudentFromDoc(document)
                     student.let { it1 -> studentList.add(it1) }
                 }
                 trySend(Result.Success(studentList))
             }.addOnFailureListener {
                 trySend(Result.Error(it))
             }
             awaitClose { docRef.isComplete }
         }
    }

    override suspend fun subTractAttendance(branch: String, studentID: String, subject: String) {
        val docRef = firebaseFirestore.collection("branches").document(branch).collection("students").document(studentID)

        docRef.get().addOnSuccessListener { document ->
            if (document != null) {
                val attendance = (document.data?.get("attendance") as Map<String, List<Long>>).toMutableMap()
                val attendanceList = attendance[subject]?.toMutableList()
                attendanceList?.set(0, attendanceList[0] -1)
                attendance[subject] = attendanceList!!
                docRef.update("attendance", attendance).addOnSuccessListener {
                    return@addOnSuccessListener
                }.addOnFailureListener {
                    Toast.makeText(null, "Failed to update attendance", Toast.LENGTH_SHORT).show()
                    return@addOnFailureListener
                }
            }
        }
    }

    /**
     * */
    override suspend fun addAttendance(branch: String, studentID: String, subject: String) {
        val docRef = firebaseFirestore.collection("branches").document(branch).collection("students").document(studentID)

        docRef.get().addOnSuccessListener { document ->
            if (document != null) {
                val attendance = (document.data?.get("attendance") as Map<String, List<Long>>).toMutableMap()
                val attendanceList = attendance[subject]?.toMutableList()
                attendanceList?.set(0, attendanceList[0] + 1)
                attendance[subject] = attendanceList!!
                docRef.update("attendance", attendance).addOnSuccessListener {
                    return@addOnSuccessListener
                }.addOnFailureListener {
                    Toast.makeText(null, "Failed to update attendance", Toast.LENGTH_SHORT).show()
                    return@addOnFailureListener
                }
            }
        }
    }

    /**
     *       This function is used to get the student details from the document snapshot
     * */
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
            attendance = students.data?.get("attendance") as Map<String, List<Long>>
        )
    }
}