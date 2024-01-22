package com.prafullkumar.campusepulse.di

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.prafullkumar.campusepulse.data.adminRepos.AdminRepository
import com.prafullkumar.campusepulse.data.adminRepos.AdminRepositoryImpl
import com.prafullkumar.campusepulse.data.OnBoardingRepository
import com.prafullkumar.campusepulse.data.OnBoardingRepositoryImpl
import com.prafullkumar.campusepulse.managers.SharedPrefManager

interface AppModule {
    val onBoardingRepository: OnBoardingRepository
}
class AppModuleImpl(
    private val context: Context
) : AppModule {

    private val firebaseAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }
    private val sharedPrefManager: SharedPrefManager by lazy {
        SharedPrefManager(context)
    }
    override val onBoardingRepository: OnBoardingRepository by lazy {
        OnBoardingRepositoryImpl(firebaseAuth, sharedPrefManager)
    }
}