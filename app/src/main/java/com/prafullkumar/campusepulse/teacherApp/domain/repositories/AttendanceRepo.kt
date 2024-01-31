package com.prafullkumar.campusepulse.teacherApp.domain.repositories

import com.prafullkumar.campusepulse.adminApp.domain.models.Student
import com.prafullkumar.campusepulse.adminApp.domain.repositories.Result
import kotlinx.coroutines.flow.Flow

interface AttendanceRepository {
    suspend fun getClassStudentList(branch: String): Flow<Result<List<Student>>>
    suspend fun subTractAttendance(branch: String, studentID: String, subject: String)
    suspend fun addAttendance(branch: String, studentID: String, subject: String)
    suspend fun addFinalAttendance(branch: String, students: List<String>, subject: String)
}
