package com.prafullkumar.campusepulse.teacherApp.attendanceScreen

import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.material3.Card
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import com.prafullkumar.campusepulse.commons.LoadingScreen
import com.prafullkumar.campusepulse.data.teacherRepos.StudentDisplayAttendance

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowClassAttendance(viewModel: ShowAttendanceViewModel, navController: NavHostController){
    val attendanceState by viewModel.attendanceState.collectAsState()
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = {
                Text(text = "Attendance")
            })
        }
    ) { paddingValues ->
        LazyColumn(contentPadding = paddingValues) {
            item {
                when (attendanceState) {
                    is AttendanceState.Loading -> {
                        LoadingScreen()
                    }
                    is AttendanceState.Success -> {
                        (attendanceState as AttendanceState.Success).attendance.forEach { attendance ->
                            StudentCard(studentDisplayAttendance = attendance)
                        }
                    }
                    is AttendanceState.Error -> {
                        Text(text = (attendanceState as AttendanceState.Error).error)
                    }
                }
            }
        }
    }
}

@Composable
private fun StudentCard(studentDisplayAttendance: StudentDisplayAttendance) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            Column {
                studentDisplayAttendance.name?.let { Text(text = it) }
                studentDisplayAttendance.rollNo?.let { Text(text = it) }
            }
            Column {
                studentDisplayAttendance.present?.let { Text(text = "  Present : ${it.toString()}") }
                studentDisplayAttendance.absent?.let { Text(text = "  Absent : ${it.toString()}") }
            }
        }
    }
}