package com.prafullkumar.campusepulse.adminApp.uiComponents

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.prafullkumar.campusepulse.R


@Composable
fun AddButton(addClass: () -> Unit = {}, addStudent: () -> Unit = {}) {
    var isExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.End
    ) {
        AnimatedVisibility(visible = isExpanded) {
            FloatingActionButton(onClick = addStudent, containerColor = MaterialTheme.colorScheme.secondaryContainer) {
                Icon(
                    painter = painterResource(id = R.drawable.student),
                    contentDescription = stringResource(R.string.add_student),
                    modifier = Modifier.size(24.dp)
                )
            }
        }
        Spacer(modifier = Modifier.padding(8.dp))
        AnimatedVisibility(visible = isExpanded) {
            FloatingActionButton(onClick = addClass, containerColor = MaterialTheme.colorScheme.secondaryContainer) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_school_24),
                    contentDescription = stringResource(
                        R.string.add_class
                    ),
                    modifier = Modifier.size(24.dp)
                )
            }
        }
        Spacer(modifier = Modifier.padding(8.dp))
        FloatingActionButton(onClick = {
            isExpanded = !isExpanded
        }, containerColor = MaterialTheme.colorScheme.primary) {
            Icon(imageVector = if (isExpanded) Icons.Default.Clear else Icons.Default.Add, contentDescription = null)
        }
    }
}