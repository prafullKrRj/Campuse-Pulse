package com.prafullkumar.campusepulse.adminApp.addBranchScreen

import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.prafullkumar.campusepulse.adminApp.addStudentScreen.AddTexts
import com.prafullkumar.campusepulse.adminApp.addStudentScreen.SelectFromOptions
import com.prafullkumar.campusepulse.commons.TopAppBar
import com.prafullkumar.campusepulse.model.NewBranch
import kotlinx.coroutines.flow.update

/**
 *  @see com.prafullkumar.campusepulse.adminApp.uiComponents.AddBranchScreen
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddBranchScreen(viewModel: AddBranchViewModel, navController: NavController) {
    val branchState by viewModel.state.collectAsState()
    Log.d("newBranch", "AddBranchScreen: ${branchState.newBranch}")
    var backDialog by remember { mutableStateOf(false) }
    BackHandler {

    }
    Scaffold(
        topBar = {
            TopAppBar(
                heading = "Add Branch", actionIcon = Icons.Default.Done, actionIconClicked = {
                    if (branchState.newBranch.branchName.isNotEmpty() && branchState.newBranch.year.isNotEmpty()) {
                        viewModel.addBranch()
                        viewModel.resetNewBranch()
                        navController.popBackStack()
                    } else {
                        Toast.makeText(navController.context, "Please fill all the fields", Toast.LENGTH_SHORT).show()
                    }

                }, navIcon = Icons.Default.ArrowBack, navIconClicked = {
                    backDialog = true
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(contentPadding = paddingValues, modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
            item {
                SelectFromOptions(
                    label = "Select Year",
                    list = mutableListOf("1st", "2nd", "3rd", "4th"),
                ) {
                    viewModel.state.update { state ->
                        state.copy(
                            newBranch = state.newBranch.copy(
                                year = it
                            )
                        )
                    }
                }
            }
            item {
                AddTexts(
                    label = "Branch",
                    onValueChange = {
                        viewModel.state.update { state ->
                            state.copy(
                                newBranch = state.newBranch.copy(
                                    branchName = it
                                )
                            )
                        }
                    }
                )
            }
            item {
                AddSubjects {
                    viewModel.state.update { state ->
                        state.copy(
                            newBranch = state.newBranch.copy(
                                subjects = it
                            )
                        )
                    }
                }
            }
            item {
                AddBatches {
                    viewModel.state.update { state ->
                        state.copy(
                            newBranch = state.newBranch.copy(
                                batches = it
                            )
                        )
                    }
                }
            }
            if (branchState.newBranch.batches.isNotEmpty() && branchState.newBranch.subjects.isNotEmpty()) {
                item {
                    AddTimeTable(viewModel = viewModel, navController)
                }
            }
        }
    }


    if (backDialog) {
        AlertDialog(
            onDismissRequest = { backDialog = false },
            icon = { Icon(imageVector = Icons.Filled.Info, contentDescription = "Info") },
            title = {
                Text(text = "Title")
            },
            text = {
                Text("Do you want to exit without saving")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        backDialog = false
                        navController.popBackStack()
                    }
                ) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        backDialog = false
                    }
                ) {
                    Text("Dismiss")
                }
            }
        )
    }
}
@Composable
fun AddBatches(listOfBatches: (List<String>) -> Unit) {
    val batches by rememberSaveable { mutableStateOf(mutableListOf<String>()) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "Add batches", modifier = Modifier.weight(.3f))
        Column(modifier = Modifier
            .weight(.7f)
            .padding(vertical = 4.dp, horizontal = 16.dp)) {
            batches.forEachIndexed { index, it ->
                Text(text = "${index+1}. $it", fontWeight = SemiBold)
                Spacer(modifier = Modifier.padding(4.dp))
            }
            var text by rememberSaveable { mutableStateOf("") }

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = text,
                onValueChange = { text = it },
                label = { Text("Batch") },
                trailingIcon = {
                    IconButton(onClick = {
                        batches.add(text.uppercase())
                        listOfBatches(batches)
                        text = ""
                    }) {
                        Icon(Icons.Filled.Send, contentDescription = "Add")
                    }
                },
                shape = RoundedCornerShape(40),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primaryContainer,
                    unfocusedBorderColor = MaterialTheme.colorScheme.primaryContainer,
                    focusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                    unfocusedContainerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    }
}
@Composable
fun AddSubjects(listOfSubjects: (List<String>) -> Unit) {
    val subjects by rememberSaveable { mutableStateOf(mutableListOf<String>()) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "Add subjects", modifier = Modifier.weight(.3f))
        Column(modifier = Modifier
            .weight(.7f)
            .padding(vertical = 4.dp, horizontal = 16.dp)) {
            subjects.forEachIndexed { index, it ->
                Text(text = "${index+1}. $it", fontWeight = SemiBold)
                Spacer(modifier = Modifier.padding(4.dp))
            }
            var text by rememberSaveable { mutableStateOf("") }

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = text,
                onValueChange = { text = it },
                label = { Text("Subject") },
                trailingIcon = {
                    IconButton(onClick = {
                        subjects.add(text.uppercase())
                        listOfSubjects(subjects)
                        text = ""
                    }) {
                        Icon(Icons.Filled.Send, contentDescription = "Add")
                    }
                },
                shape = RoundedCornerShape(40),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primaryContainer,
                    unfocusedBorderColor = MaterialTheme.colorScheme.primaryContainer,
                    focusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                    unfocusedContainerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    }
}