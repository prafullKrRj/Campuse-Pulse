package com.prafullkumar.campusepulse.studentApp.notes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.prafullkumar.campusepulse.R
import com.prafullkumar.campusepulse.commons.TopAppBar

@Composable
fun TasksScreen(viewModel: NotesViewModel) {
    Scaffold(
        topBar = {
            TopAppBar(label = R.string.tasks)
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {

        }
    }
}