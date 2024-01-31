package com.prafullkumar.campusepulse.teacherApp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.prafullkumar.campusepulse.R
import com.prafullkumar.campusepulse.ViewModelProvider
import com.prafullkumar.campusepulse.studentApp.BottomNavigationBar
import com.prafullkumar.campusepulse.teacherApp.ui.attendanceScreen.DisplayAttendance
import com.prafullkumar.campusepulse.teacherApp.ui.attendanceScreen.DisplayAttendanceViewModel
import com.prafullkumar.campusepulse.teacherApp.ui.attendanceScreen.ShowClassAttendance
import com.prafullkumar.campusepulse.teacherApp.ui.homeScreen.TeacherHomeScreen
import com.prafullkumar.campusepulse.teacherApp.ui.homeScreen.TeacherProfileScreen
import com.prafullkumar.campusepulse.teacherApp.ui.homeScreen.TeacherViewModel
import com.prafullkumar.campusepulse.teacherApp.ui.takeAttendanceScreen.TakeAttendance

@Composable
fun TeacherNavGraph(signOut: () -> Unit) {
    val teacherNavController = rememberNavController()
    var isTakeAttendance by rememberSaveable { mutableStateOf(false) }
    val viewModels = listOf(
        viewModel<TeacherViewModel>(factory = ViewModelProvider.getTeacherViewModel()),
        viewModel<DisplayAttendanceViewModel>(factory = ViewModelProvider.getDisplayAttendanceViewModel()),
    )
    Scaffold (
        bottomBar = {
            if (!isTakeAttendance) {
                BottomNavigationBar(sNavController = teacherNavController, selected = 0, items = TeacherConsts.items)
            }
        }
    ){ paddingValues ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)) {
            NavHost(
                navController = teacherNavController,
                startDestination = TeacherScreens.HOME.name
            ) {
                composable(TeacherScreens.HOME.name) {
                    isTakeAttendance = false
                    TeacherHomeScreen(viewModel = viewModels[0] as TeacherViewModel, navController = teacherNavController)
                }
                composable(TeacherScreens.TAKE_ATTENDANCE.name + "/{branch}") { navBackStackEntry ->
                    isTakeAttendance = true
                    navBackStackEntry.arguments?.getString("branch")?.let { branch ->
                        TakeAttendance(viewModel = viewModel(factory = ViewModelProvider.getAttendanceViewModel(branch)), teacherNavController)
                    }
                }
                composable(TeacherScreens.PROFILE.name) {
                    TeacherProfileScreen(viewModel = viewModels[0] as TeacherViewModel) {
                        signOut()
                    }
                }
                composable(TeacherScreens.ATTENDANCE.name) {
                    DisplayAttendance(
                        viewModel = viewModels[1] as DisplayAttendanceViewModel,
                        navController = teacherNavController
                    )
                }
                composable(TeacherScreens.SHOW_ATTENDANCE.name + "/{branch}") { navBackStackEntry ->
                    navBackStackEntry.arguments?.getString("branch")?.let { branch ->
                        ShowClassAttendance(
                            viewModel = viewModel(factory = ViewModelProvider.getShowAttendanceViewModel(branch)),
                            navController = teacherNavController,
                        )
                    }
                }
            }
        }
    }
}

enum class TeacherScreens {
    HOME,
    PROFILE,
    ATTENDANCE,
    TAKE_ATTENDANCE,
    SHOW_ATTENDANCE,
}
@Immutable
object TeacherConsts {
    val items = listOf(
        "Home" to TeacherScreens.HOME.name to R.drawable.baseline_school_24,
        "Attendance" to TeacherScreens.ATTENDANCE.name to R.drawable.baseline_data_saver_off_24,
        "Profile" to TeacherScreens.PROFILE.name to R.drawable.baseline_person_24,
    )
}