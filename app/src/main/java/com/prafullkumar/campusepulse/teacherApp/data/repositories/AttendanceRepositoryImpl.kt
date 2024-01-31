@file:Suppress("UNCHECKED_CAST")

package com.prafullkumar.campusepulse.teacherApp.data.repositories

import android.widget.Toast
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.prafullkumar.campusepulse.adminApp.domain.models.Student
import com.prafullkumar.campusepulse.adminApp.domain.repositories.Result
import com.prafullkumar.campusepulse.teacherApp.domain.repositories.AttendanceRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow


@Suppress("LABEL_NAME_CLASH")
class AttendanceRepositoryImpl(
    private val firebaseFirestore: FirebaseFirestore
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

    override suspend fun addFinalAttendance(
        branch: String,
        students: List<String>,
        subject: String
    ) {
        val docRef = firebaseFirestore.collection("branches").document(branch).collection("students")
        for (student in students) {
            val docRef2 = docRef.document(student)
            docRef2.get().addOnSuccessListener { document ->
                if (document != null) {
                    val attendance = (document.data?.get("attendance") as Map<String, List<Long>>).toMutableMap()
                    val attendanceList = attendance[subject]?.toMutableList()
                    attendanceList?.set(1, attendanceList[1] + 1)
                    attendance[subject] = attendanceList!!
                    docRef2.update("attendance", attendance).addOnSuccessListener {
                        return@addOnSuccessListener
                    }.addOnFailureListener {
                        Toast.makeText(null, "Failed to update attendance", Toast.LENGTH_SHORT).show()
                        return@addOnFailureListener
                    }
                }
            }
        }
    }

    /**
     *       This function is used to get the student details from the document snapshot
     * */
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