package com.prafullkumar.campusepulse.studentApp.ui.notes

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.prafullkumar.campusepulse.R
import com.prafullkumar.campusepulse.commons.TopAppBar
import com.prafullkumar.campusepulse.studentApp.data.local.TasksEntity
import java.text.SimpleDateFormat
import java.util.Date

@Composable
fun TasksScreen(viewModel: TasksViewModel) {
    var completedTasks by rememberSaveable {
        mutableStateOf<List<TasksEntity>>(emptyList())
    }
    var incompleteTasks by rememberSaveable {
        mutableStateOf<List<TasksEntity>>(emptyList())
    }
    LaunchedEffect(viewModel.completedTasks) {
        viewModel.completedTasks.observeForever { tasks ->
            completedTasks = tasks
            Log.d("TasksScreen", "TasksScreen: $tasks")
        }
    }
    LaunchedEffect(viewModel.incompleteTasks) {
        viewModel.incompleteTasks.observeForever { tasks ->
            incompleteTasks = tasks
        }
    }
    var openDialog by remember { mutableStateOf(false) }
    var deleteDialog by remember { mutableStateOf(false) }
    Scaffold(
        topBar = {
            TopAppBar(label = R.string.tasks, actionIconClicked = {
                deleteDialog = true
            }, actionIcon = Icons.Filled.Delete)
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
            InCompletedTasksSection(viewModel, tasks = incompleteTasks)
            CompletedTaskSection(viewModel, tasks = completedTasks)
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
                            if (text.isEmpty()) return@TextButton
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
        if (deleteDialog) {
            AlertDialog(
                onDismissRequest = { openDialog = false },
                icon = { Icon(imageVector = Icons.Filled.Info, contentDescription = "Info") },
                title = {
                    Text(text = "Delete")
                },
                text = {
                    Text("Select the tasks you want to delete")
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            viewModel.deleteAll()
                            deleteDialog = false
                        }
                    ) {
                        Text("Delete All")
                    }
                    TextButton(onClick = {
                        viewModel.deleteCompleted()
                        deleteDialog = false
                    }) {
                        Text("Delete Completed")
                    }
                    TextButton(onClick = {
                        viewModel.deleteInCompleted()
                        deleteDialog = false
                    }) {
                        Text("Delete InCompleted")
                    }

                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            deleteDialog = false
                        }
                    ) {
                        Text("Dismiss")
                    }
                }
            )
        }
    }
}

@Composable
fun CompletedTaskSection(viewModel: TasksViewModel, tasks: List<TasksEntity>) {
    if (tasks.isEmpty()) return
    Text(text = "Completed Tasks", modifier = Modifier.padding(8.dp), fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
    tasks.sortedByDescending { it.time }.forEach {
        TaskRow(viewModel = viewModel, task = it)
    }
}

@Composable
fun InCompletedTasksSection(viewModel: TasksViewModel, tasks: List<TasksEntity>) {
    if (tasks.isEmpty()) return
    Text(text = "InCompleted Tasks", modifier = Modifier.padding(8.dp), fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
    tasks.sortedByDescending { it.time }.forEach {
        TaskRow(viewModel = viewModel, task = it)
    }
}
@Composable
fun TaskRow(viewModel: TasksViewModel, task: TasksEntity) {
    val checked by remember { mutableStateOf(task.isCompleted) }
    ElevatedCard(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)) {
        Column(modifier = Modifier, horizontalAlignment = Alignment.End) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                verticalAlignment = CenterVertically
            ) {
                Checkbox(
                    checked = checked,
                    onCheckedChange = {
                        viewModel.addTask(task.copy(isCompleted = it))
                    },
                    modifier = Modifier.weight(.1f)
                )
                Text(
                    text = task.task,
                    modifier = Modifier
                        .weight(.8f)
                        .padding(horizontal = 8.dp),
                    textDecoration = if (checked) TextDecoration.LineThrough else TextDecoration.None
                )
                IconButton(
                    onClick = {
                        viewModel.deleteTask(task)
                    },
                    modifier = Modifier.weight(.1f)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = "Delete Task"
                    )
                }
            }
            Text(text = converter(task.time), modifier = Modifier.padding(8.dp), fontSize = 12.sp, fontWeight = FontWeight.Light)
        }
    }
}
fun converter(time: Long): String {
    val sdf = SimpleDateFormat("HH:mm dd/MM/yyyy")
    return sdf.format(Date(time))
}