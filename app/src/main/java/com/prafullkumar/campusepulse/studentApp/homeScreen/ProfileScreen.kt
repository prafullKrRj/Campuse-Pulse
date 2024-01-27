package com.prafullkumar.campusepulse.studentApp.homeScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.prafullkumar.campusepulse.adminApp.models.Student
import com.prafullkumar.campusepulse.commons.ErrorScreen
import com.prafullkumar.campusepulse.commons.LoadingScreen
import com.prafullkumar.campusepulse.commons.TopAppBar

@Composable
fun ProfileScreen(viewModel: StudentViewModel, navController: NavController) {
    val state by viewModel.studentScreenState.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(
                heading = "Profile",
                navIcon = Icons.Default.ArrowBack,
                navIconClicked = {
                    navController.popBackStack()
                }
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            when(state) {
                is StudentScreenState.Initial -> {
                    Text(text = "Initial")
                }
                is StudentScreenState.Loading -> {
                    LoadingScreen()
                }
                is StudentScreenState.Success -> {
                    ProfileMainUI(student = (state as StudentScreenState.Success).studentData.first)
                }
                is StudentScreenState.Error -> {
                    ErrorScreen {
                        viewModel.getStudentDetails()
                    }
                }
            }
        }
    }
}

@Composable
fun ProfileMainUI(student: Student) {
    LazyColumn {
        item {
            ProfileField(label = "Name", value = student.fname + " " + student.lname)
        }
        item {
            student.branch?.let { ProfileField(label = "Branch", value = it) }
        }
        item {
            ProfileField(label = "DOB", value = student.dob.toString())
        }
        item {
            ProfileField(label = "Batch", value = student.batch.toString())
        }
        item {
            ProfileField(label = "Roll No.", value = student.rollNo.toString())
        }
        item {
            ProfileField(label = "Admission No.", value = student.admNo.toString())
        }
        item {
            ProfileField(label = "Phone", value = student.phoneNo.toString())
        }
    }
}
@Composable
fun ProfileField(label: String, value: String) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp).fillMaxWidth()) {
        Text(text = label, fontSize = 18.sp, fontWeight = FontWeight.Bold, modifier = Modifier.weight(.35f))
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = value, fontSize = 18.sp, modifier = Modifier.weight(.65f))
    }
    Spacer(modifier = Modifier.height(8.dp))
}
@Composable
fun NameSection(student: Student) {

}