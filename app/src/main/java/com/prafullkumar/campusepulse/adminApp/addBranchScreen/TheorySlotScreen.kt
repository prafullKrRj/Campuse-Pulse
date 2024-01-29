package com.prafullkumar.campusepulse.adminApp.addBranchScreen

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
import com.prafullkumar.campusepulse.adminApp.addStudentScreen.AddNumbers
import com.prafullkumar.campusepulse.adminApp.addStudentScreen.SelectFromOptions
import com.prafullkumar.campusepulse.commons.TopAppBar
import com.prafullkumar.campusepulse.model.ClassDetails

@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun TheorySlotScreen(day: String, viewModel: AddBranchViewModel, navController: NavController) {
    val scrollState = rememberScrollState()
    val state by viewModel.teachers.collectAsState()
    val startTime = rememberTimePickerState()
    val endTime = rememberTimePickerState()
    var teacher by rememberSaveable {
        mutableStateOf("")
    }
    var subject by rememberSaveable {
        mutableStateOf("")
    }
    var lh by rememberSaveable {
        mutableStateOf("")
    }
    val branchState by viewModel.state.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(
                heading = "Add Theory", actionIcon = Icons.Default.Done,
                actionIconClicked = {
                             if (teacher.isNotEmpty() && subject.isNotEmpty() && lh.isNotEmpty()) {
                                 branchState.newBranch.timeTable[day]?.add(
                                     ClassDetails(
                                         subTeacher = mutableListOf(Pair(subject, teacher)),
                                         startTime = "${startTime.hour}:${startTime.minute}",
                                         endTime = "${endTime.hour}:${endTime.minute}",
                                         lh = lh
                                     )
                                 )
                             }
                    navController.popBackStack()
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
                SelectFromOptions(
                    label = "Teacher",
                    list = (state as TeacherDetailsState.Success).data.map { it.name }.toMutableList()
                ) {
                    teacher = it
                }
                SelectFromOptions(label = "Subject", list = branchState.newBranch.subjects.toMutableList()) {
                    subject = it
                }
                AddNumbers(label = "LH") {
                    lh = it
                }
            }
        }
    }
}