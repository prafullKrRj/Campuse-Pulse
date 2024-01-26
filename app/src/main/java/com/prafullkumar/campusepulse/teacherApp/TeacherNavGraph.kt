package com.prafullkumar.campusepulse.teacherApp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.prafullkumar.campusepulse.R
import com.prafullkumar.campusepulse.commons.TopAppBar
import com.prafullkumar.campusepulse.managers.ViewModelProvider
import com.prafullkumar.campusepulse.studentApp.BottomNavigationBar
import com.prafullkumar.campusepulse.teacherApp.homeScreen.TeacherHomeScreen
import com.prafullkumar.campusepulse.teacherApp.homeScreen.TeacherProfileScreen
import com.prafullkumar.campusepulse.teacherApp.homeScreen.TeacherViewModel
import com.prafullkumar.campusepulse.teacherApp.takeAttendanceScreen.TakeAttendance
import com.prafullkumar.campusepulse.teacherApp.takeAttendanceScreen.TakeAttendanceViewModel

@Composable
fun TeacherNavGraph() {
    val teacherNavController = rememberNavController()
    var isTakeAttendance by rememberSaveable { mutableStateOf(false) }
    val viewModels = listOf(
        viewModel<TeacherViewModel>(factory = ViewModelProvider.getTeacherViewModel()),
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
                    TeacherHomeScreen(viewModel = viewModels[0], navController = teacherNavController)
                }
                composable(TeacherScreens.TAKE_ATTENDANCE.name + "/{branch}") { navBackStackEntry ->
                    isTakeAttendance = true
                    navBackStackEntry.arguments?.getString("branch")?.let { branch ->
                        TakeAttendance(viewModel = viewModel(factory = ViewModelProvider.getTakeAttendanceViewModel(branch)))
                    }
                }
                composable(TeacherScreens.PROFILE.name) {
                    TeacherProfileScreen(viewModel = viewModels[0])
                }
                composable(TeacherScreens.ATTENDANCE.name) {
                    Text(text = "Attendance")
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
}
@Immutable
object TeacherConsts {
    val items = listOf(
        "Home" to TeacherScreens.HOME.name to R.drawable.baseline_school_24,
        "Attendance" to TeacherScreens.ATTENDANCE.name to R.drawable.baseline_data_saver_off_24,
        "Profile" to TeacherScreens.PROFILE.name to R.drawable.baseline_person_24,
    )
}