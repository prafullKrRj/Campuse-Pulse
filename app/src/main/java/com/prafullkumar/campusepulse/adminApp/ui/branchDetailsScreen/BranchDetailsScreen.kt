package com.prafullkumar.campusepulse.adminApp.ui.branchDetailsScreen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.prafullkumar.campusepulse.R
import com.prafullkumar.campusepulse.adminApp.ui.addBranchScreen.ImageWindow
import com.prafullkumar.campusepulse.adminApp.ui.addBranchScreen.Uploaded
import com.prafullkumar.campusepulse.adminApp.domain.models.Branch
import com.prafullkumar.campusepulse.commons.LoadingScreen
import com.prafullkumar.campusepulse.commons.TopAppBar
import com.prafullkumar.campusepulse.studentApp.ui.homeScreen.ProfileField
import kotlinx.coroutines.launch

@Composable
fun BranchDetailsScreen(viewModel: BranchDetailsViewModel, navController: NavController) {
    val branchDetails by viewModel.branchDetails.collectAsState()
    val students by viewModel.studentsFlow.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(heading = "Branch Details", navIcon = Icons.Default.ArrowBack, navIconClicked = {
                navController.popBackStack()
            })
        }
    ) { paddingValues ->
        Column(modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()) {
            when (branchDetails) {
                is BranchDetailsState.Error -> {
                    (branchDetails as BranchDetailsState.Error).error?.let { Text(text = it) }
                }
                is BranchDetailsState.Loading -> {
                    LoadingScreen()
                }
                is BranchDetailsState.Success -> {
                    BranchDetailsSection(branch = (branchDetails as BranchDetailsState.Success).branchDetails, viewModel)
                }

                else -> {}
            }
            when (students) {
                is StudentsState.Error -> {
                    (students as StudentsState.Error).error?.let { Text(text = it) }
                }
                is StudentsState.Loading -> {
                    LoadingScreen()
                }
                is StudentsState.Success -> {
                    Text(text = (students as StudentsState.Success).studentList.toString())
                }
                else -> {}
            }

        }
    }
}

@Composable
fun BranchDetailsSection(branch: Branch, viewModel: BranchDetailsViewModel) {
    var isNewImage by rememberSaveable {
        mutableStateOf(false)
    }
    var newImage by rememberSaveable {
        mutableStateOf<Uri?>(null)
    }
    val getImages = rememberLauncherForActivityResult(contract = ActivityResultContracts.PickVisualMedia()) { uri ->
        uri?.let {
            isNewImage = true
            viewModel.updateTimeTable(
                branchId = branch.id!!,
                uri = it
            )
            newImage = it
        }
    }
    val uploaded by viewModel.upLoaded.collectAsState()
    val scope = rememberCoroutineScope()
    LazyColumn {
        item {
            branch.name?.let { ProfileField(label = "Name", value = it) }
        }
        item {
            branch.strength?.let { ProfileField(label = "Strength", value = it.toString()) }
        }
        item {
            branch.batches?.let { ProfileField(label = "Batches", value = it.toString()) }
        }
        item {
            ProfileField(label = "Subjects", value = branch.subjects.toString())
        }
        item {
            Text(text = "Time Table", textAlign = TextAlign.Center, fontSize = 20.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(16.dp))
            if (!isNewImage) {
                TimeTableImage(data = branch.timeTable?: "")
            }
        }
        item {
            Button(onClick = {
                scope.launch {
                    getImages.launch(
                        PickVisualMediaRequest(mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                }
            },
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Text(text = "Change Time Table")
            }
        }
    }
    when (uploaded) {
        is Uploaded.Error -> {
            (uploaded as Uploaded.Error).msg?.let { Text(text = it) }
        }
        is Uploaded.Loading -> {
            LoadingScreen()
        }
        is Uploaded.Success -> {
            ImageWindow(image = newImage)
        }
        else -> {}
    }
}

@Composable
fun TimeTableImage(data: String) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current).data(data).build(),
        contentDescription = null,
        contentScale = ContentScale.Fit,
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        placeholder = painterResource(id = R.drawable.loading_img),
        error = painterResource(id = R.drawable.ic_broken_image)
    )
}