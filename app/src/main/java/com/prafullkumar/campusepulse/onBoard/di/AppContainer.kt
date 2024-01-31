package com.prafullkumar.campusepulse.onBoard.di

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.prafullkumar.campusepulse.managers.SharedPrefManager
import com.prafullkumar.campusepulse.onBoard.data.repositories.OnBoardingRepositoryImpl
import com.prafullkumar.campusepulse.onBoard.domain.repositories.OnBoardingRepository

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