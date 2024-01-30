package com.prafullkumar.campusepulse.adminApp.addBranchScreen

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.compose.material3.ExperimentalMaterial3Api
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.prafullkumar.campusepulse.adminApp.addStudentScreen.AddTexts
import com.prafullkumar.campusepulse.adminApp.addStudentScreen.SelectFromOptions
import com.prafullkumar.campusepulse.commons.LoadingScreen
import com.prafullkumar.campusepulse.commons.TopAppBar
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddBranchScreen(viewModel: AddBranchViewModel, navController: NavController) {
    val branchState by viewModel.state.collectAsState()
    var backDialog by remember { mutableStateOf(false) }
    val getImages = rememberLauncherForActivityResult(contract = ActivityResultContracts.PickVisualMedia()) { uri ->
        uri?.let { viewModel.uploadTimeTable(it) }
    }
    val scope = rememberCoroutineScope()
    val uploaded by viewModel.upLoaded.collectAsState()
    BackHandler {

    }

    Scaffold(
        topBar = {
            TopAppBar(
                heading = "Add Branch", actionIcon = Icons.Default.Done, actionIconClicked = {
                    if (branchState.newBranch.branchName.isNotEmpty() && branchState.newBranch.year.isNotEmpty()) {
                        viewModel.addBranch()
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
            item {
                Button(onClick = {
                    scope.launch {
                        getImages.launch(PickVisualMediaRequest(mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly))
                    }
                }) {
                    Text(text = "Upload Time Table")
                }
            }
            item {
                branchState.newBranch.timeTable?.let {
                    ImageWindow(image = it)
                }
            }
        }
        when (uploaded) {
            is Uploaded.Error -> {
                Toast.makeText(navController.context, (uploaded as Uploaded.Error).msg, Toast.LENGTH_SHORT).show()
            }
            Uploaded.Initial -> {

            }
            Uploaded.Loading -> {
                LoadingScreen()
            }
            is Uploaded.Success -> {
                Toast.makeText(navController.context, (uploaded as Uploaded.Success).url, Toast.LENGTH_SHORT).show()
                navController.popBackStack()
            }
        }
    }
    if (backDialog) {
        AlertDialog(
            onDismissRequest = { backDialog = false },
            icon = { Icon(imageVector = Icons.Filled.Info, contentDescription = "Info") },
            title = {
                Text(text = "Save Changes")
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
fun ImageWindow(image: Uri?) {
    /*if (image != null) {
        Image(bitmap = image.asImageBitmap(), contentDescription = null, contentScale = ContentScale.FillWidth)
    }*/
    val context = LocalContext.current
    val bitmap: ImageBitmap? = image?.let { loadBitmapFromUri(it, context) }
    if (bitmap != null) {
        Image(bitmap = bitmap, contentDescription = null)
    }
}
private fun loadBitmapFromUri(uri: Uri, context: Context): ImageBitmap? {
    return try {
        val inputStream = context.contentResolver.openInputStream(uri)
        val bitmap = BitmapFactory.decodeStream(inputStream)
        bitmap?.asImageBitmap()
    } catch (e: Exception) {
        // Handle exceptions, such as IOException or SecurityException
        null
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
                        if (text.isNotEmpty()) {
                            batches.add(text.uppercase())
                            listOfBatches(batches)
                            text = ""
                        }
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
    var subjects by rememberSaveable { mutableStateOf(mutableListOf<String>()) }
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
                Row(modifier = Modifier, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(text = "${index + 1}. $it", fontWeight = SemiBold)
                    IconButton(onClick = {
                        subjects = subjects.toMutableList().apply { removeAt(index) }
                        listOfSubjects(subjects)
                    }) {
                        Icon(imageVector = Icons.Default.Delete, contentDescription = null)
                    }
                }
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
                        if (text.isNotEmpty()) {
                            subjects.add(text.uppercase())
                            listOfSubjects(subjects)
                            text = ""
                        }
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