package com.prafullkumar.campusepulse.di

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.prafullkumar.campusepulse.data.adminRepos.AdminRepository
import com.prafullkumar.campusepulse.data.adminRepos.AdminRepositoryImpl
import com.prafullkumar.campusepulse.data.adminRepos.BranchDetailsRepository
import com.prafullkumar.campusepulse.data.adminRepos.BranchDetailsRepositoryImpl

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

    override val adminRepository: AdminRepository by lazy {
        AdminRepositoryImpl(firebaseAuth, firebaseFirestore, context)
    }
    override val branchDetailsRepository: BranchDetailsRepository by lazy {
        BranchDetailsRepositoryImpl(firestore = firebaseFirestore)
    }
}