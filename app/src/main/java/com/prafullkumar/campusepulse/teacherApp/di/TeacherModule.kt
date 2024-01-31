package com.prafullkumar.campusepulse.teacherApp.di

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.prafullkumar.campusepulse.managers.SharedPrefManager
import com.prafullkumar.campusepulse.teacherApp.data.repositories.AttendanceRepositoryImpl
import com.prafullkumar.campusepulse.teacherApp.data.repositories.DisplayAttendRepositoryImpl
import com.prafullkumar.campusepulse.teacherApp.data.repositories.TeacherRepositoryImpl
import com.prafullkumar.campusepulse.teacherApp.domain.repositories.AttendanceRepository
import com.prafullkumar.campusepulse.teacherApp.domain.repositories.DisplayAttendRepository
import com.prafullkumar.campusepulse.teacherApp.domain.repositories.TeacherRepository

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
    private val sharedPrefManager: SharedPrefManager by lazy {
        SharedPrefManager(context)
    }
    override val teacherRepository: TeacherRepository by lazy{
        TeacherRepositoryImpl(firebaseFirestore, firebaseAuth, sharedPrefManager)
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