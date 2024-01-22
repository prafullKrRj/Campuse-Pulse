package com.prafullkumar.campusepulse.di

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.prafullkumar.campusepulse.data.studentRepo.StudentRepository
import com.prafullkumar.campusepulse.data.studentRepo.StudentRepositoryImpl

interface StudentModule {
    val studentRepository: StudentRepository
}
class StudentModuleImpl(
    private val context: Context
) : StudentModule {
    private val firebaseAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    private val firebaseFirestore: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }
    override val studentRepository: StudentRepository by lazy {
        StudentRepositoryImpl(firebaseFirestore, firebaseAuth)
    }

}