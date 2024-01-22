package com.prafullkumar.campusepulse.studentApp

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun StudentHomeScreen(viewModel: StudentViewModel) {
    val state by viewModel.studentScreenState.collectAsState()
    when(state) {
        is StudentScreenState.Initial -> {
            Text("Initial")
        }
        is StudentScreenState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        is StudentScreenState.Success -> {
            Text(text = (state as StudentScreenState.Success).studentData.toString())
        }
        is StudentScreenState.Error -> {
            (state as StudentScreenState.Error).error?.let { Text(text = it) }
        }
    }
}