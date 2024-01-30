package com.prafullkumar.campusepulse.teacherApp.homeScreen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.FilledTonalButton
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.prafullkumar.campusepulse.commons.ErrorScreen
import com.prafullkumar.campusepulse.commons.LoadingScreen
import com.prafullkumar.campusepulse.commons.TopAppBar
import com.prafullkumar.campusepulse.studentApp.homeScreen.ProfileField

@Composable
fun TeacherProfileScreen(viewModel: TeacherViewModel, signOut: () -> Unit) {
    val teacherDetails by viewModel.teacherState.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(heading = "Profile")
        }
    ) { paddingValues ->

        when (teacherDetails) {
            is TeacherState.Error -> {
                ErrorScreen {
                    viewModel.getClassList()
                }
            }
            TeacherState.Loading -> {
                LoadingScreen()
            }
            is TeacherState.Success -> {
                val details = teacherDetails as TeacherState.Success
                LazyColumn(contentPadding = paddingValues) {
                    item {
                        details.data.name?.let { ProfileField(label = "Name", value = it) }
                    }
                    item {
                        details.data.branches?.let {
                            ProfileField(
                                label = "Branches",
                                value = it.toString()
                            )
                        }
                    }
                    item {
                        FilledTonalButton(
                            modifier = Modifier.fillMaxWidth().padding(32.dp),
                            onClick = {
                                viewModel.signOut()
                                signOut()
                            }
                        ) {
                            Text("Sign Out")
                        }
                    }
                }
            }
        }
    }
}