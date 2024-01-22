package com.prafullkumar.campusepulse.managers

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.prafullkumar.campusepulse.CampusePulseApp
import com.prafullkumar.campusepulse.adminApp.homeScreen.AdminViewModel
import com.prafullkumar.campusepulse.adminApp.branchDetailsScreen.BranchDetailsViewModel
import com.prafullkumar.campusepulse.presentations.onBoardingScreen.OnBoardViewModel
import com.prafullkumar.campusepulse.studentApp.StudentViewModel
import com.prafullkumar.campusepulse.studentApp.assistant.AssistantsViewModel
import com.prafullkumar.campusepulse.studentApp.notes.NotesViewModel
import com.prafullkumar.campusepulse.studentApp.noticeScreen.NoticeViewModel
import com.prafullkumar.campusepulse.studentApp.profileScreen.ProfileViewModel

object ViewModelProvider {

    fun getMainViewModel() = viewModelFactory {
        initializer {
            val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as CampusePulseApp)
            val onBoardingRepository = application.appModule.onBoardingRepository
            OnBoardViewModel(onBoardingRepository)
        }
    }
    fun getAdminViewModel() = viewModelFactory {
        initializer {
            val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as CampusePulseApp)
            val adminRepository = application.adminModule.adminRepository
            AdminViewModel(adminRepository)
        }
    }
    fun getBranchDetailsViewModel(id: String) = viewModelFactory {
        initializer {
            val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as CampusePulseApp)
            val branchDetailsRepository = application.adminModule.branchDetailsRepository
            BranchDetailsViewModel(branchDetailsRepository, id)
        }
    }
    fun getStudentViewModel() = viewModelFactory {
        initializer {
            val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as CampusePulseApp)
            val studentRepository = application.studentModule.studentRepository
            StudentViewModel(studentRepository)
        }
    }
    fun getNotesViewModel() = viewModelFactory {
        initializer {
            val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as CampusePulseApp)
            val studentRepository = application.studentModule.notesRepository
            NotesViewModel(studentRepository)
        }
    }
    fun getStudentProfileViewModel() = viewModelFactory {
        initializer {
            val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as CampusePulseApp)
            val repository = application.studentModule.profileRepository
            ProfileViewModel(repository)
        }
    }
    fun getStudentAssistantViewModel() = viewModelFactory {
        initializer {
            val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as CampusePulseApp)
            val repository = application.studentModule.assistantRepository
            AssistantsViewModel(repository)
        }
    }
    fun getStudentNoticeViewModel() = viewModelFactory {
        initializer {
            val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as CampusePulseApp)
            val repository = application.studentModule.noticesRepository
            NoticeViewModel(repository)
        }
    }
}
