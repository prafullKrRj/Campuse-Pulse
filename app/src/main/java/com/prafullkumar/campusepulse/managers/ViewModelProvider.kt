package com.prafullkumar.campusepulse.managers

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.prafullkumar.campusepulse.CampusePulseApp
import com.prafullkumar.campusepulse.adminApp.addBranchScreen.AddBranchViewModel
import com.prafullkumar.campusepulse.adminApp.branchDetailsScreen.BranchDetailsViewModel
import com.prafullkumar.campusepulse.adminApp.homeScreen.AdminViewModel
import com.prafullkumar.campusepulse.presentations.onBoardingScreen.OnBoardViewModel
import com.prafullkumar.campusepulse.studentApp.assistant.AssistantsViewModel
import com.prafullkumar.campusepulse.studentApp.homeScreen.StudentViewModel
import com.prafullkumar.campusepulse.studentApp.notes.TasksViewModel
import com.prafullkumar.campusepulse.studentApp.noticeScreen.NoticeViewModel
import com.prafullkumar.campusepulse.teacherApp.attendanceScreen.DisplayAttendanceViewModel
import com.prafullkumar.campusepulse.teacherApp.attendanceScreen.ShowAttendanceViewModel
import com.prafullkumar.campusepulse.teacherApp.homeScreen.TeacherViewModel
import com.prafullkumar.campusepulse.teacherApp.takeAttendanceScreen.AttendanceViewModel

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
    fun addBranchViewModel() = viewModelFactory {
        initializer {
            val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as CampusePulseApp)
            val addBranchRepository = application.adminModule.adminRepository
            AddBranchViewModel(addBranchRepository)
        }
    }
    /**
     *  Student app View Models
     * */
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
            val studentRepository = application.studentModule.tasksRepository
            TasksViewModel(studentRepository)
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

    /**
     *
     *         Teacher app View Models
     * */

    fun getTeacherViewModel() = viewModelFactory {
        initializer {
            val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as CampusePulseApp)
            val teacherRepository = application.teacherModule.teacherRepository
            TeacherViewModel(teacherRepository)
        }
    }
    fun getAttendanceViewModel(branch: String) = viewModelFactory {
        initializer {
            val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as CampusePulseApp)
            val takeAttendanceRepository = application.teacherModule.takeAttendanceRepository
            AttendanceViewModel(takeAttendanceRepository, branch)
        }
    }
    fun getDisplayAttendanceViewModel() = viewModelFactory {
        initializer {
            val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as CampusePulseApp)
            val displayAttendanceRepository = application.teacherModule.displayAttendRepository
            DisplayAttendanceViewModel(displayAttendanceRepository)
        }
    }

    fun getShowAttendanceViewModel(branch: String) = viewModelFactory {
        initializer {
            val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as CampusePulseApp)
            val displayAttendanceRepository = application.teacherModule.displayAttendRepository
            ShowAttendanceViewModel(displayAttendanceRepository, branch)
        }
    }
}
