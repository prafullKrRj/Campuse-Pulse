package com.prafullkumar.campusepulse.adminApp.uiComponents

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.prafullkumar.campusepulse.adminApp.AdminViewModel
import com.prafullkumar.campusepulse.adminApp.Student

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
                StudentForm() {
                    adminViewModel.addStudent(it)
                }
            }
        }
    }
}
@Composable
fun StudentForm(onSaveClick: (Student) -> Unit = {}) {
    var firstName by rememberSaveable { mutableStateOf("") }
    var lastName by rememberSaveable { mutableStateOf("") }
    var rollNo by rememberSaveable { mutableStateOf("") }
    var admissionNo by rememberSaveable { mutableStateOf("") }
    var branch by rememberSaveable { mutableStateOf("") }
    var dob by rememberSaveable { mutableStateOf("") }
    var phoneNumber by rememberSaveable { mutableStateOf("") }
    var batch by rememberSaveable { mutableStateOf("") }
    Column(modifier = Modifier
        .padding(16.dp)
        .fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        InputFieldText(label = "First Name", value = firstName, onValueChange = { firstName = it })
        InputFieldText(label = "Last Name", value = lastName, onValueChange = { lastName = it })
        InputFieldNumber(label = "Roll No", value = rollNo, onValueChange = { rollNo = it })
        InputFieldNumber(label = "Admission No", value = admissionNo, onValueChange = { admissionNo = it })
        InputFieldText(label = "Branch", value = branch, onValueChange = { branch = it })
        InputFieldText(label = "Batch", value = batch, onValueChange = { batch = it })
        DateSelectorField(label = "Date of Birth", value = dob, onValueChange = { dob = it })
        InputFieldNumber(label = "Phone Number", value = phoneNumber, onValueChange = { phoneNumber = it })
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            onClick = {
                onSaveClick(
                    Student(
                        fName = firstName,
                        lName = lastName,
                        phone = phoneNumber.toLong(),
                        branch = branch,
                        rollNo = rollNo.toLong(),
                        admissionNo = admissionNo.toLong(),
                        dob = dob,
                        batch = batch
                    )
                )
                firstName = ""
                lastName = ""
                rollNo = ""
                admissionNo = ""
                branch = ""
                dob = ""
                phoneNumber = ""
                batch = ""
            },
            enabled = firstName.isNotEmpty() && lastName.isNotEmpty() && rollNo.isNotEmpty() && admissionNo.isNotEmpty() && branch.isNotEmpty() && dob.isNotEmpty() && phoneNumber.isNotEmpty(),
        ) {
            Text("Submit")
        }
    }
}