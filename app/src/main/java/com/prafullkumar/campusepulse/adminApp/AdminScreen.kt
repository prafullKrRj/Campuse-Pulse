package com.prafullkumar.campusepulse.adminApp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import com.prafullkumar.campusepulse.R

@Composable
fun AdminScreen(
    adminViewModel: AdminViewModel
) {
    val state by adminViewModel.state.collectAsState()
    Scaffold (
        topBar = {
            TopBar()
        },
        floatingActionButton = {
            AddStudentButton {
                adminViewModel.addStudent()
            }
        }
    ){ paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            when (state) {
                is AdminState.Initial -> {
                    Text(text = "Initial")
                }
                is AdminState.Success -> {
                    AdminMainUI((state as AdminState.Success).branches)
                }
                is AdminState.Error -> {
                    Text(text = (state as AdminState.Error).error)
                }
                is AdminState.Loading -> {
                    CircularProgressIndicator()
                }
            }
        }
    }
}

@Composable
fun AdminMainUI(branches: MutableList<Branch>) {
    LazyColumn {
        items(branches.size) { index ->
            Text(text = branches[index].name)
        }
    }
}
@Composable
fun AddStudentButton(onAdd: () -> Unit = {}) {
    FloatingActionButton(onClick = {
        onAdd()
    }) {
        Icon(imageVector = Icons.Default.Add, contentDescription = null)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar() {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.admin),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        navigationIcon = {
            IconButton(onClick = { /* doSomething() */ }) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "Menu"
                )
            }
        },
        actions = {
            IconButton(onClick = { /* doSomething() */ }) {
                Icon(
                    imageVector = Icons.Filled.Favorite,
                    contentDescription = "Favorite"
                )
            }
        }
    )
}