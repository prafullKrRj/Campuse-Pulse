package com.prafullkumar.campusepulse.teacherApp.takeAttendanceScreen

import android.widget.Toast
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
import com.prafullkumar.campusepulse.adminApp.models.Student
import com.prafullkumar.campusepulse.commons.ErrorScreen
import com.prafullkumar.campusepulse.commons.LoadingScreen
import com.prafullkumar.campusepulse.commons.TopAppBar

@Composable
fun TakeAttendance(viewModel: TakeAttendanceViewModel) {
    val state by viewModel.state.collectAsState()
    BackHandler {

    }
    Scaffold(topBar = {
        TopAppBar(heading = "Take Attendance")
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
                        TakeAttendanceSuccess(students = (state as TakeAttendanceState.Success).data, viewModel = viewModel)
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
}

@Composable
fun TakeAttendanceSuccess(students: List<Student>, viewModel: TakeAttendanceViewModel) {
    students.forEachIndexed { index, student ->
        StudentCard(
            student = student,
            onChecked = {
                viewModel.attendanceList.add(student)
                viewModel.addAttendance(studentID = student.admissionNo)
            },
            onUnchecked = {
                viewModel.attendanceList.remove(student)
                viewModel.subTractAttendance(studentID = student.admissionNo)
            }
        )
    }
}
@Composable
fun StudentCard(student: Student,onChecked: () -> Unit, onUnchecked: () -> Unit = {}) {
    val context = LocalContext.current
    Toast.makeText(context, "StudentCard", Toast.LENGTH_SHORT).show()
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
            modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
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
                    text = "${student.fName} ${student.lName}",
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

@Composable
fun SaveAlertDialog(
    viewModel: TakeAttendanceViewModel
) {
    var openDialog by remember { mutableStateOf(true) }

    if (openDialog) {
        AlertDialog(
            onDismissRequest = { openDialog = false },
            icon = { Icon(imageVector = Icons.Filled.Info, contentDescription = "Info") },
            title = {
                Text(text = "Do you want to save the attendance?")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        openDialog = false
                    }
                ) {
                    Text("Ok")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        openDialog = false
                    }
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}