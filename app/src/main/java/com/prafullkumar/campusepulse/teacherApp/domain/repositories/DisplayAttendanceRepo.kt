package com.prafullkumar.campusepulse.teacherApp.domain.repositories

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.prafullkumar.campusepulse.adminApp.domain.models.Student
import com.prafullkumar.campusepulse.adminApp.domain.repositories.Result
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

interface DisplayAttendRepository {
    suspend fun getClasses(): Flow<Result<TeacherDetails>>
    suspend fun getAttendance(branch: String, subject: String): Flow<Result<List<StudentDisplayAttendance>>>
}

data class StudentDisplayAttendance(
    val name: String?,
    val rollNo: String?,
    val present: Long?,
    val absent: Long?,
)