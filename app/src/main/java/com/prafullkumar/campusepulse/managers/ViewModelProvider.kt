package com.prafullkumar.campusepulse.managers

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.prafullkumar.campusepulse.CampusePulseApp
import com.prafullkumar.campusepulse.adminApp.AdminViewModel
import com.prafullkumar.campusepulse.presentations.onBoardingScreen.OnBoardViewModel

object ViewModelProvider {

    fun getMainViewModel() = viewModelFactory {
        initializer {
            val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as CampusePulseApp)
            val onBoardingRepository = application.appContainer.onBoardingRepository
            OnBoardViewModel(onBoardingRepository)
        }
    }
    fun getAdminViewModel() = viewModelFactory {
        initializer {
            val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as CampusePulseApp)
            val adminRepository = application.appContainer.adminRepository
            AdminViewModel(adminRepository)
        }
    }
}