package com.prafullkumar.campusepulse.teacherApp.ui.takeAttendanceScreen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.material3.Checkbox
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.Card
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.prafullkumar.campusepulse.adminApp.domain.models.Student
import com.prafullkumar.campusepulse.commons.ErrorScreen
import com.prafullkumar.campusepulse.commons.LoadingScreen
import com.prafullkumar.campusepulse.commons.TopAppBar
import com.prafullkumar.campusepulse.goBackStack

@Composable
fun TakeAttendance(viewModel: AttendanceViewModel, navController: NavController) {
    val state by viewModel.state.collectAsState()
    var openDialog by remember { mutableStateOf(false) }

    BackHandler {

    }
    Scaffold(topBar = {
        TopAppBar(heading = "Take Attendance",
            navIcon = Icons.Default.ArrowBack,
            navIconClicked = {
                openDialog = true
            }, actionIcon = Icons.Default.Done, actionIconClicked = {
                viewModel.onSavedClicked()
                navController.goBackStack()
            })
    }) { paddingValues ->
        LazyColumn(contentPadding = paddingValues) {
            item {
                Spacer(modifier = Modifier.width(8.dp))
            }
            item {
                when (state) {
                    is TakeAttendanceState.Loading -> {
                        LoadingScreen()
                    }
                    is TakeAttendanceState.Success -> {
                        TakeAttendanceSuccess(students = ((state as TakeAttendanceState.Success).data).sortedBy {
                                                                                                                it.rollNo
                        }, viewModel = viewModel)
                    }
                    is TakeAttendanceState.Error -> {
                        ErrorScreen {
                            viewModel.getClassStudentList()
                        }
                    }
                }
            }
        }
    }
    if (openDialog) {
        AlertDialog(
            onDismissRequest = { openDialog = false },
            icon = { Icon(imageVector = Icons.Filled.Info, contentDescription = "Info") },
            title = {
                Text(text = "Attendance")
            },
            text = {
                Text(text = "Save the attendance?")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        openDialog = false
                        navController.goBackStack()
                        viewModel.onSavedClicked()
                    }
                ) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        openDialog = false
                        navController.goBackStack()
                    }
                ) {
                    Text("Dismiss")
                }
            }
        )
    }
}

@Composable
fun TakeAttendanceSuccess(students: List<Student>, viewModel: AttendanceViewModel) {
    students.forEachIndexed { _, student ->
        StudentCard(
            student = student,
            onChecked = {
                viewModel.attendanceList.add(student)
                viewModel.addAttendance(studentID = student.admNo)
            },
            onUnchecked = {
                viewModel.attendanceList.remove(student)
                viewModel.subTractAttendance(studentID = student.admNo)
            }
        )
    }
}
@Composable
fun StudentCard(student: Student, onChecked: () -> Unit, onUnchecked: () -> Unit = {}) {
    LocalContext.current
    var checked by remember { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                if (checked) {
                    onUnchecked()
                } else {
                    onChecked()
                }
                checked = !checked
            },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
            ) {
                Text(
                    text = student.rollNo.toString(),
                )
                Text(
                    text = "${student.fname} ${student.lname}",
                )
            }
            Checkbox(
                enabled = false,
                checked = checked,
                onCheckedChange = { checked = it }
            )
        }

    }
}

