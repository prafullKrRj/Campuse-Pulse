package com.prafullkumar.campusepulse.studentApp.notes

import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.material3.TextButton
import androidx.compose.material3.AlertDialog
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.Icons
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.prafullkumar.campusepulse.R
import com.prafullkumar.campusepulse.commons.TopAppBar
import com.prafullkumar.campusepulse.data.local.TasksEntity

@Composable
fun TasksScreen(viewModel: TasksViewModel) {
    val tasks = viewModel.tasks.value
    var openDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(label = R.string.tasks)
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    openDialog = true
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Add Task"
                )
            }
        },

    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            tasks?.forEach {
                Text(text = it.task)
            }
        }

        if (openDialog) {
            var text by rememberSaveable { mutableStateOf("") }
            AlertDialog(
                onDismissRequest = { openDialog = false },
                icon = { Icon(imageVector = Icons.Filled.Info, contentDescription = "Info") },
                title = {
                    Text(text = "Title")
                },
                text = {
                    Column(
                        modifier = Modifier,
                    ) {
                        Text(text = "Add Task")
                        OutlinedTextField(
                            value = text,
                            onValueChange = { text = it },
                            label = { Text("Task") }
                        )
                    }
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            openDialog = false
                            viewModel.addTask(TasksEntity(task = text, isCompleted = false, time = System.currentTimeMillis()))
                            text = ""
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
