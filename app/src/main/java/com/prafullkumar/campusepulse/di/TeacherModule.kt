package com.prafullkumar.campusepulse.di

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.prafullkumar.campusepulse.data.teacherRepos.AttendanceRepository
import com.prafullkumar.campusepulse.data.teacherRepos.AttendanceRepositoryImpl
import com.prafullkumar.campusepulse.data.teacherRepos.DisplayAttendRepository
import com.prafullkumar.campusepulse.data.teacherRepos.DisplayAttendRepositoryImpl
import com.prafullkumar.campusepulse.data.teacherRepos.TeacherRepository
import com.prafullkumar.campusepulse.data.teacherRepos.TeacherRepositoryImpl

interface TeacherModule {
    val teacherRepository: TeacherRepository
    val takeAttendanceRepository: AttendanceRepository
    val displayAttendRepository: DisplayAttendRepository
}
class TeacherModuleImpl(
    private val context: Context
) : TeacherModule {

    private val firebaseAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    private val firebaseFirestore: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }
    override val teacherRepository: TeacherRepository by lazy{
        TeacherRepositoryImpl(firebaseFirestore, firebaseAuth)
    }
    override val takeAttendanceRepository: AttendanceRepository by lazy {
        AttendanceRepositoryImpl(
            firebaseFirestore = firebaseFirestore,
            context
        )
    }
    override val displayAttendRepository: DisplayAttendRepository by lazy {
        DisplayAttendRepositoryImpl(
            firestore = firebaseFirestore,
            firebaseAuth = firebaseAuth
        )
    }
}