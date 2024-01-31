package com.prafullkumar.campusepulse

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.prafullkumar.campusepulse.adminApp.ui.addBranchScreen.AddBranchViewModel
import com.prafullkumar.campusepulse.adminApp.ui.branchDetailsScreen.BranchDetailsViewModel
import com.prafullkumar.campusepulse.adminApp.AdminViewModel
import com.prafullkumar.campusepulse.onBoard.ui.OnBoardViewModel
import com.prafullkumar.campusepulse.studentApp.ui.assistant.AssistantsViewModel
import com.prafullkumar.campusepulse.studentApp.ui.homeScreen.StudentViewModel
import com.prafullkumar.campusepulse.studentApp.ui.notes.TasksViewModel
import com.prafullkumar.campusepulse.studentApp.ui.noticeScreen.NoticeViewModel
import com.prafullkumar.campusepulse.teacherApp.ui.attendanceScreen.DisplayAttendanceViewModel
import com.prafullkumar.campusepulse.teacherApp.ui.attendanceScreen.ShowAttendanceViewModel
import com.prafullkumar.campusepulse.teacherApp.ui.homeScreen.TeacherViewModel
import com.prafullkumar.campusepulse.teacherApp.ui.takeAttendanceScreen.AttendanceViewModel

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
    fun addTeacherViewModel() = viewModelFactory {
        initializer {
            val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as CampusePulseApp)
            val addTeacherRepository = application.adminModule.adminRepository
            AddBranchViewModel(addTeacherRepository)
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
