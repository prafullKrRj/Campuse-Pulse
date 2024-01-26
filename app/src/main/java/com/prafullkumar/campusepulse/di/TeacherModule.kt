package com.prafullkumar.campusepulse.di

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.prafullkumar.campusepulse.data.teacherRepos.TakeAttendanceRepository
import com.prafullkumar.campusepulse.data.teacherRepos.TakeAttendanceRepositoryImpl
import com.prafullkumar.campusepulse.data.teacherRepos.TeacherRepository
import com.prafullkumar.campusepulse.data.teacherRepos.TeacherRepositoryImpl

interface TeacherModule {
    val teacherRepository: TeacherRepository
    val takeAttendanceRepository: TakeAttendanceRepository
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
    override val takeAttendanceRepository: TakeAttendanceRepository by lazy {
        TakeAttendanceRepositoryImpl(
            firebaseFirestore = firebaseFirestore,
            firebaseAuth = firebaseAuth,
            context
        )
    }

}