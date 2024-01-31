package com.prafullkumar.campusepulse.teacherApp.ui.homeScreen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.prafullkumar.campusepulse.commons.ErrorScreen
import com.prafullkumar.campusepulse.commons.LoadingScreen
import com.prafullkumar.campusepulse.commons.TopAppBar
import com.prafullkumar.campusepulse.studentApp.ui.homeScreen.ProfileField

@Composable
fun TeacherProfileScreen(viewModel: TeacherViewModel, signOut: () -> Unit) {
    val teacherDetails by viewModel.teacherState.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(heading = "Profile")
        }
    ) { paddingValues ->
        var openDialog by remember { mutableStateOf(false) }
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
                                openDialog = true
                            }
                        ) {
                            Text("Sign Out")
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
                    Text(text = "LogOut")
                },
                text = {
                    Text("Want to log Out?")
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            openDialog = false
                            signOut()
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
}