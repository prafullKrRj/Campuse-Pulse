package com.prafullkumar.campusepulse.adminApp.addStudentScreen

import androidx.compose.material3.OutlinedTextField
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.prafullkumar.campusepulse.adminApp.homeScreen.AdminViewModel
import com.prafullkumar.campusepulse.adminApp.uiComponents.InputFieldNumber
import com.prafullkumar.campusepulse.adminApp.uiComponents.InputFieldText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddStudentScreen(viewModel: AdminViewModel, navController: NavController) {
    Scaffold (
        topBar = {
            TopAppBar(
                title = { Text(text = "Add Student") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ){ paddingValues ->
        LazyColumn(
            contentPadding = paddingValues,
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                AddTexts(label = "First Name:", onValueChange = {
                    viewModel.newStudent = viewModel.newStudent.copy(
                        fName  = it
                    )
                })
            }
            item {
                AddTexts(label = "Last Name:", onValueChange = {
                    viewModel.newStudent = viewModel.newStudent.copy(
                        lName = it
                    )
                })
            }
            item {
                AddNumbers(label = "Roll No:", onValueChange = {
                    viewModel.newStudent = viewModel.newStudent.copy(
                        rollNo = it
                    )
                })
            }
            item {
                if (viewModel.branches.value != null) {
                    SelectFromOptions(label = "Branch", list = viewModel.branches.value!!.map {
                        it?.name
                    }.toMutableList(), onValueChange = {
                        viewModel.newStudent = viewModel.newStudent.copy(
                            branch = it
                        )
                    })
                }
            }
            item {
                AddNumbers(label = "Adm No:", onValueChange = {
                    viewModel.newStudent = viewModel.newStudent.copy(
                        admissionNo = it
                    )
                })
            }
            item {
                AddNumbers(label = "Phone:", onValueChange = {
                    viewModel.newStudent = viewModel.newStudent.copy(
                        phone = it
                    )
                })
            }
            item {
                if (viewModel.newStudent.branch != null) {
                    SelectFromOptions(
                        label = "Batch",
                        list = viewModel.branches.value!!.find {
                            it.name == viewModel.newStudent.branch
                        }?.batches?.toMutableList() ?: mutableListOf(),
                    ) {
                        viewModel.newStudent = viewModel.newStudent.copy(
                            batch = it
                        )
                    }
                }
            }
            item {
                DateInputSample {
                    viewModel.newStudent = viewModel.newStudent.copy(
                        dob = it
                    )
                }
            }
            item {
                Button(onClick = {
                    viewModel.addStudent()
                    navController.popBackStack()
                    viewModel.getBranches()
                }) {
                    Text(text = "Add Student")
                }
            }
        }
    }
}
@Composable
fun DateInputSample(onSave: (String) -> Unit = {}) {
    var day by rememberSaveable { mutableStateOf("") }
    var month by rememberSaveable { mutableStateOf("") }
    var year by rememberSaveable { mutableStateOf("") }
    Column(modifier = Modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp, horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(text = "DOB:", modifier = Modifier.weight(.3f))
            Column(
                modifier = Modifier
                    .weight(.225f).padding(end = 16.dp)
            ) {
                OutlinedTextField(
                    value = day,
                    onValueChange = { day = it },
                    label = { Text("DD") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }
            Column(
                modifier = Modifier
                    .weight(.225f).padding(end = 16.dp)
            ) {
                OutlinedTextField(
                    value = month,
                    onValueChange = { month = it },
                    label = { Text("MM") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }
            Column(
                modifier = Modifier
                    .weight(.25f).padding(end = 16.dp)
            ) {
                OutlinedTextField(
                    value = year,
                    onValueChange = { year = it },
                    label = { Text("YY") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }
        }
        TextButton(onClick = {
            onSave("$day/$month/$year")
        }, modifier = Modifier.align(Alignment.End)) {
            Text(text = "Save")
        }
    }
}
@Composable
fun AddNumbers(label: String = "", onValueChange: (String) -> Unit = {}) {
    var fieldValue by rememberSaveable { mutableStateOf("") }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "$label:", modifier = Modifier.weight(.3f))
        InputFieldNumber(modifier = Modifier.weight(.7f), label = "", value = fieldValue, onValueChange = {
            fieldValue = it
            onValueChange(it)
        })
    }
}
@Composable
fun AddTexts(label: String = "", onValueChange: (String) -> Unit = {}) {
    var fieldValue by rememberSaveable { mutableStateOf("") }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "$label:", modifier = Modifier.weight(.3f))
        InputFieldText(modifier = Modifier.weight(.7f), label = "", value = fieldValue, onValueChange = {
            fieldValue = it
            onValueChange(it)
        })
    }
}
@Composable
fun SelectFromOptions(
    label: String = "",
    list: MutableList<String?> = mutableListOf(),
    onValueChange: (String) -> Unit = {}
) {
    var openDialog by remember { mutableStateOf(false) }
    var radioSelected by remember { mutableIntStateOf(0) }
    var selectedBranch by remember { mutableStateOf("") }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            modifier = Modifier.weight(.3f),
            text = "$label:",
        )
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .weight(.7f)
                .padding(vertical = 4.dp, horizontal = 16.dp), onClick = {
                openDialog = true
            }) {
            Text(text = selectedBranch.ifEmpty { "Select $label" })
        }
    }
    if (openDialog) {
        AlertDialog(
            onDismissRequest = { openDialog = false },
            title = {
                Text(text = "Select $label")
            },
            text = {
                Column {
                    list.forEachIndexed { index, value ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            RadioButton(
                                selected = radioSelected == index,
                                onClick = {
                                    radioSelected = index
                                },
                                modifier = Modifier.semantics { contentDescription = "Option 1" }
                            )
                            if (value != null) {
                                Text(text = value)
                            }
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        openDialog = false
                        selectedBranch = list[radioSelected].toString()
                        onValueChange(selectedBranch)
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