package com.prafullkumar.campusepulse.studentApp.di

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.prafullkumar.campusepulse.managers.SharedPrefManager
import com.prafullkumar.campusepulse.studentApp.data.local.AppDatabase
import com.prafullkumar.campusepulse.studentApp.data.repositories.AssistantsRepoImpl
import com.prafullkumar.campusepulse.studentApp.data.repositories.NoticesRepositoryImpl
import com.prafullkumar.campusepulse.studentApp.data.repositories.StudentRepositoryImpl
import com.prafullkumar.campusepulse.studentApp.data.repositories.TasksRepositoryImpl
import com.prafullkumar.campusepulse.studentApp.domain.AssistantsRepo
import com.prafullkumar.campusepulse.studentApp.domain.NoticesRepository
import com.prafullkumar.campusepulse.studentApp.domain.StudentRepository
import com.prafullkumar.campusepulse.studentApp.domain.TasksRepository

interface StudentModule {
    val studentRepository: StudentRepository
    val assistantRepository: AssistantsRepo
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
        StudentRepositoryImpl(firebaseFirestore, firebaseAuth, sharedPrefManager, AppDatabase.getDatabase(context).appDao())
    }
    override val assistantRepository: AssistantsRepo by lazy {
        AssistantsRepoImpl()
    }
    override val noticesRepository: NoticesRepository by lazy {
        NoticesRepositoryImpl(context)
    }
    override val tasksRepository: TasksRepository by lazy {
        TasksRepositoryImpl(AppDatabase.getDatabase(context).appDao(), context)
    }
}