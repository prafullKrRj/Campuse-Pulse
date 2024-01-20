package com.prafullkumar.campusepulse.di

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.prafullkumar.campusepulse.data.AdminRepository
import com.prafullkumar.campusepulse.data.AdminRepositoryImpl
import com.prafullkumar.campusepulse.data.OnBoardingRepository
import com.prafullkumar.campusepulse.data.OnBoardingRepositoryImpl
import com.prafullkumar.campusepulse.managers.SharedPrefManager

interface AppModule {
    val onBoardingRepository: OnBoardingRepository
    val adminRepository: AdminRepository
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
    override val adminRepository: AdminRepository by lazy {
        AdminRepositoryImpl(firebaseAuth)
    }
}