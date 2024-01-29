package com.prafullkumar.campusepulse.adminApp.addBranchScreen

import androidx.compose.runtime.remember
import androidx.compose.material3.TextButton
import androidx.compose.material3.Icon
import androidx.compose.material3.AlertDialog
import androidx.compose.material.icons.filled.Info
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TimeInput
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.prafullkumar.campusepulse.adminApp.addStudentScreen.SelectFromOptions
import com.prafullkumar.campusepulse.commons.TopAppBar
import com.prafullkumar.campusepulse.model.ClassDetails

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PracticalSlotScreen(day: String, viewModel: AddBranchViewModel, navController: NavController) {
    val scrollState = rememberScrollState()
    val state by viewModel.teachers.collectAsState()
    val startTime = rememberTimePickerState(is24Hour = false)
    val endTime = rememberTimePickerState(is24Hour = false)
    var openDialog by remember { mutableStateOf(false) }
    val branchState by viewModel.state.collectAsState()
    val list by rememberSaveable {
        mutableStateOf(mutableListOf<Pair<String, String>>())
    }
    var selectedBatch by rememberSaveable {
        mutableStateOf("")
    }
    Scaffold(
        topBar = {
            TopAppBar(
                heading = "Add Practical", actionIcon = Icons.Default.Done,
                actionIconClicked = {
                    if (list.isNotEmpty()) {
                        branchState.newBranch.timeTable[day]?.add(
                            ClassDetails(
                                subTeacher = list,
                                startTime = "${startTime.hour}:${startTime.minute}",
                                endTime = "${endTime.hour}:${endTime.minute}",
                                type = "P",
                                lh = "Lab"
                            )
                        )
                        navController.popBackStack()
                    }
                },
                navIconClicked = {
                    navController.popBackStack()
                },
                navIcon = Icons.Default.Clear,
            )
        }
    ) { paddingValues ->
        Column(
            Modifier
                .padding(paddingValues)
                .verticalScroll(scrollState),
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp, horizontal = 16.dp), horizontalArrangement = Arrangement.SpaceEvenly) {
                Text(
                    modifier = Modifier.weight(.3f),
                    text = "Start Time:",
                )
                TimeInput(state = startTime, modifier = Modifier
                    .fillMaxWidth()
                    .weight(.7f)
                    .padding(vertical = 4.dp, horizontal = 16.dp))
            }
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp, horizontal = 16.dp), horizontalArrangement = Arrangement.SpaceEvenly) {
                Text(
                    modifier = Modifier.weight(.3f),
                    text = "End Time:",
                )
                TimeInput(state = endTime, modifier = Modifier
                    .fillMaxWidth()
                    .weight(.7f)
                    .padding(vertical = 4.dp, horizontal = 16.dp))
            }
            if (state is TeacherDetailsState.Success) {
                branchState.newBranch.batches.forEach { batch ->
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
                        Button(onClick = {
                            openDialog = true
                            selectedBatch = batch
                        }) {
                            Text(text = batch)
                        }
                        Column {
                            Text(text = "")
                            Text(text = "")
                            Text(text = "")
                        }
                    }
                }
            }
        }
    }

    if (openDialog) {
        var teacher by remember {
            mutableStateOf("")
        }
        var subject by remember {
            mutableStateOf("")
        }
        AlertDialog(
            onDismissRequest = { openDialog = false },
            icon = { Icon(imageVector = Icons.Filled.Info, contentDescription = "Info") },
            title = {
                Text(text = "Add Details")
            },
            text = {
                   Column {
                          SelectFromOptions(
                            label = "Teacher",
                            list = (state as TeacherDetailsState.Success).data.map { it.name }.toMutableList()
                          ) {
                            teacher = it.uppercase()
                          }
                          SelectFromOptions(label = "Subject", list = branchState.newBranch.subjects.toMutableList()) {
                            subject = it.uppercase()
                          }
                   }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        openDialog = false
                        if (teacher.isNotEmpty() && subject.isNotEmpty()) {
                            list.add(
                                Pair(
                                    "$selectedBatch-$subject",
                                    teacher
                                )
                            )
                            teacher = ""
                            subject = ""
                        }
                    }
                ) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        openDialog = false
                    }
                ) {
                    Text("Dismiss")
                }
            }
        )
    }
}