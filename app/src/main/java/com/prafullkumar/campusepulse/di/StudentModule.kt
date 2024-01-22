package com.prafullkumar.campusepulse.di

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.prafullkumar.campusepulse.data.studentRepo.AssistantsRepository
import com.prafullkumar.campusepulse.data.studentRepo.AssistantsRepositoryImpl
import com.prafullkumar.campusepulse.data.studentRepo.NotesRepository
import com.prafullkumar.campusepulse.data.studentRepo.NotesRepositoryImpl
import com.prafullkumar.campusepulse.data.studentRepo.NoticesRepository
import com.prafullkumar.campusepulse.data.studentRepo.NoticesRepositoryImpl
import com.prafullkumar.campusepulse.data.studentRepo.ProfileRepository
import com.prafullkumar.campusepulse.data.studentRepo.ProfileRepositoryImpl
import com.prafullkumar.campusepulse.data.studentRepo.StudentRepository
import com.prafullkumar.campusepulse.data.studentRepo.StudentRepositoryImpl

interface StudentModule {
    val studentRepository: StudentRepository
    val notesRepository: NotesRepository
    val profileRepository: ProfileRepository
    val assistantRepository: AssistantsRepository
    val noticesRepository: NoticesRepository
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
    override val notesRepository: NotesRepository by lazy {
        NotesRepositoryImpl()
    }
    override val profileRepository: ProfileRepository by lazy {
        ProfileRepositoryImpl()
    }
    override val assistantRepository: AssistantsRepository by lazy {
        AssistantsRepositoryImpl()
    }
    override val noticesRepository: NoticesRepository by lazy {
        NoticesRepositoryImpl(context)
    }

}