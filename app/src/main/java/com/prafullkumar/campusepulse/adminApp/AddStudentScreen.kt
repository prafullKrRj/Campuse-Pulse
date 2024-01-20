package com.prafullkumar.campusepulse.adminApp

import androidx.compose.material3.Button
import androidx.compose.runtime.derivedStateOf
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.TextButton
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePicker
import androidx.compose.foundation.clickable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddStudentScreen(adminViewModel: AdminViewModel, navController: NavController) {
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
                StudentForm {
                    adminViewModel.addStudent(it)
                }
            }
        }
    }
}

@Composable
fun StudentForm(onSaveClick: (Student) -> Unit = {}) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var rollNo by remember { mutableStateOf("") }
    var admissionNo by remember { mutableStateOf("") }
    var branch by remember { mutableStateOf("") }
    var dob by remember { mutableStateOf("09/11/2004") }
    var phoneNumber by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        InputFieldText(label = "First Name", value = firstName, onValueChange = { firstName = it })
        InputFieldText(label = "Last Name", value = lastName, onValueChange = { lastName = it })
        InputFieldNumber(label = "Roll No", value = rollNo, onValueChange = { rollNo = it })
        InputFieldNumber(label = "Admission No", value = admissionNo, onValueChange = { admissionNo = it })
        InputFieldText(label = "Branch", value = branch, onValueChange = { branch = it })
        DateSelectorField(label = "Date of Birth", value = dob, onValueChange = { dob = it })
        InputFieldNumber(label = "Phone Number", value = phoneNumber, onValueChange = { phoneNumber = it })
        Spacer(modifier = Modifier.height(16.dp))


        Button(
            onClick = {
                onSaveClick(
                    Student(
                        fName = firstName,
                        lName = lastName,
                        phone = phoneNumber.toInt(),
                        branch = branch,
                        rollNo = rollNo.toInt(),
                        admissionNo = admissionNo.toInt(),
                        dob = dob
                    )
                )
            },
            enabled = firstName.isNotEmpty() && lastName.isNotEmpty() && rollNo.isNotEmpty() && admissionNo.isNotEmpty() && branch.isNotEmpty() && dob.isNotEmpty() && phoneNumber.isNotEmpty(),
        ) {
            Text("Text")
        }

    }
}

@Composable
fun InputFieldText(
    label: String,
    value: String,
    onValueChange: (String) -> Unit
) {

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 16.dp),
        shape = RoundedCornerShape(40),
        singleLine = true
    )
}

@Composable
fun InputFieldNumber(
    label: String,
    value: String,
    onValueChange: (String) -> Unit
) {

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 16.dp),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Decimal
        ),
        shape = RoundedCornerShape(40),
        singleLine = true
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateSelectorField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    var openDialog by remember { mutableStateOf(false) }
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 16.dp)
            .clickable {
                openDialog = true
            },
        enabled = false,
        shape = RoundedCornerShape(40),
    )


    if (openDialog) {
        val datePickerState = rememberDatePickerState()
        val confirmEnabled by remember {
            derivedStateOf { datePickerState.selectedDateMillis != null }
        }
        DatePickerDialog(
            onDismissRequest = {
                openDialog = false
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        openDialog = false
                    },
                    enabled = confirmEnabled
                ) {
                    Text("OK")
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
        ) {
            DatePicker(state = datePickerState)
        }
    }
}
