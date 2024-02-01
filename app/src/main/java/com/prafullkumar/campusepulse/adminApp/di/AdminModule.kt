package com.prafullkumar.campusepulse.adminApp.di

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.prafullkumar.campusepulse.adminApp.data.repositories.AdminRepositoryImpl
import com.prafullkumar.campusepulse.adminApp.data.repositories.BranchDetailsRepositoryImpl
import com.prafullkumar.campusepulse.adminApp.domain.repositories.AdminRepository
import com.prafullkumar.campusepulse.adminApp.domain.repositories.BranchDetailsRepository
import com.prafullkumar.campusepulse.managers.SharedPrefManager

interface AdminModule {
    val adminRepository: AdminRepository
    val branchDetailsRepository: BranchDetailsRepository
}
class AdminModuleImpl(
    private val context: Context
) : AdminModule {

    private val firebaseAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }
    private val firebaseFirestore: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }
    private val sharedPrefManager: SharedPrefManager by lazy {
        SharedPrefManager(context)
    }
    override val adminRepository: AdminRepository by lazy {
        AdminRepositoryImpl(firebaseAuth, firebaseFirestore, context)
    }
    override val branchDetailsRepository: BranchDetailsRepository by lazy {
        BranchDetailsRepositoryImpl(firestore = firebaseFirestore)
    }
}