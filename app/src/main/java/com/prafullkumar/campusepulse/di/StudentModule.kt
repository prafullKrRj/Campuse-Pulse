package com.prafullkumar.campusepulse.di

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.prafullkumar.campusepulse.data.local.AppDatabase
import com.prafullkumar.campusepulse.data.studentRepo.AssistantsRepository
import com.prafullkumar.campusepulse.data.studentRepo.AssistantsRepositoryImpl
import com.prafullkumar.campusepulse.data.studentRepo.NoticesRepository
import com.prafullkumar.campusepulse.data.studentRepo.NoticesRepositoryImpl
import com.prafullkumar.campusepulse.data.studentRepo.StudentRepository
import com.prafullkumar.campusepulse.data.studentRepo.StudentRepositoryImpl
import com.prafullkumar.campusepulse.data.studentRepo.TasksRepository
import com.prafullkumar.campusepulse.data.studentRepo.TasksRepositoryImpl
import com.prafullkumar.campusepulse.managers.SharedPrefManager

interface StudentModule {
    val studentRepository: StudentRepository
    val assistantRepository: AssistantsRepository
    val noticesRepository: NoticesRepository
    val tasksRepository: TasksRepository
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
    private val sharedPrefManager: SharedPrefManager by lazy {
        SharedPrefManager(context)
    }
    override val studentRepository: StudentRepository by lazy {
        StudentRepositoryImpl(firebaseFirestore, firebaseAuth, sharedPrefManager)
    }
    override val assistantRepository: AssistantsRepository by lazy {
        AssistantsRepositoryImpl()
    }
    override val noticesRepository: NoticesRepository by lazy {
        NoticesRepositoryImpl(context)
    }
    override val tasksRepository: TasksRepository by lazy {
        TasksRepositoryImpl(AppDatabase.getDatabase(context).appDao())
    }
}