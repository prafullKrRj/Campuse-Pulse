package com.prafullkumar.campusepulse.teacherApp.attendanceScreen

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.prafullkumar.campusepulse.commons.ErrorScreen
import com.prafullkumar.campusepulse.commons.LoadingScreen
import com.prafullkumar.campusepulse.commons.TopAppBar
import com.prafullkumar.campusepulse.teacherApp.TeacherScreens
import com.prafullkumar.campusepulse.teacherApp.homeScreen.ClassDetails
import com.prafullkumar.campusepulse.teacherApp.homeScreen.TeacherState

@Composable
fun DisplayAttendance(viewModel: DisplayAttendanceViewModel, navController: NavController) {
    val teacherState by viewModel.teacherState.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(
                heading = "Attendance"
            )
        }
    ) { paddingValues ->
        LazyColumn(contentPadding = paddingValues) {
            item {
                Spacer(modifier = Modifier.width(8.dp))
            }
            when (teacherState) {
                is TeacherState.Loading -> {
                    item {
                        LoadingScreen()
                    }
                }
                is TeacherState.Success -> {
                    (teacherState as TeacherState.Success).data.branches?.forEachIndexed { index, branch ->
                        item {
                            ClassDetails(
                                branch = branch,
                                index = index + 1,
                            ) {
                                navController.navigate(TeacherScreens.SHOW_ATTENDANCE.name + "/$branch")
                            }
                        }
                    }
                }
                is TeacherState.Error -> {
                    item {
                        ErrorScreen {
                            viewModel.getClassList()
                        }
                    }
                }
            }
        }
    }
}