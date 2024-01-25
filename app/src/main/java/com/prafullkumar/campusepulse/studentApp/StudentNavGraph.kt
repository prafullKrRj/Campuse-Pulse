package com.prafullkumar.campusepulse.studentApp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.prafullkumar.campusepulse.R
import com.prafullkumar.campusepulse.managers.ViewModelProvider
import com.prafullkumar.campusepulse.studentApp.assistant.AssistantScreen
import com.prafullkumar.campusepulse.studentApp.assistant.AssistantsViewModel
import com.prafullkumar.campusepulse.studentApp.attendanceScreen.AttendanceScreen
import com.prafullkumar.campusepulse.studentApp.homeScreen.StudentHomeScreenNavGraph
import com.prafullkumar.campusepulse.studentApp.homeScreen.StudentViewModel
import com.prafullkumar.campusepulse.studentApp.notes.TasksScreen
import com.prafullkumar.campusepulse.studentApp.notes.NotesViewModel
import com.prafullkumar.campusepulse.studentApp.noticeScreen.NoticeScreen
import com.prafullkumar.campusepulse.studentApp.noticeScreen.NoticeViewModel

@Composable
fun StudentNavGraph() {
    val sNavController = rememberNavController()
    val viewModels = listOf(
        viewModel<StudentViewModel>(factory = ViewModelProvider.getStudentViewModel()),
        viewModel<NotesViewModel>(factory = ViewModelProvider.getNotesViewModel()),
        viewModel<AssistantsViewModel>(factory = ViewModelProvider.getStudentAssistantViewModel()),
        viewModel<NoticeViewModel>(factory = ViewModelProvider.getStudentNoticeViewModel()),
    )
    Scaffold(
        bottomBar = {
            BottomNavigationBar(sNavController = sNavController, selected = 0, items = StudentConst.items)
        }
    ) { innerPadding ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)) {
            NavHost(navController = sNavController, startDestination = StudentScreens.TASKS.name) {
                composable(StudentScreens.HOME.name) {
                    StudentHomeScreenNavGraph(viewModel = viewModels[0] as StudentViewModel)
                }
                composable(StudentScreens.ATTENDANCE.name) {
                    AttendanceScreen(viewModel = viewModels[0] as StudentViewModel)
                }
                composable(StudentScreens.TASKS.name) {
                    TasksScreen(viewModel = viewModels[1] as NotesViewModel)
                }
                composable(StudentScreens.ASSISTANT.name) {
                    AssistantScreen(viewModel = viewModels[2] as AssistantsViewModel)
                }
                composable(StudentScreens.NOTICE.name) {
                    NoticeScreen(viewModel = viewModels[3] as NoticeViewModel)
                }
            }
        }
    }
}

@Composable
fun BottomNavigationBar(sNavController: NavController, selected: Int, items: List<Pair<Pair<String, String>, Int>>, onSelectedChange: (Int) -> Unit = {}) {
    var selectedItem by rememberSaveable { mutableIntStateOf(selected) }
    NavigationBar {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = {
                    Icon(painter = painterResource(id = item.second), contentDescription = item.first.first, modifier = Modifier.size(24.dp))
                },
                label = {
                    Text(item.first.first)
                },
                selected = selectedItem == index,
                onClick = {
                    selectedItem = index
                    onSelectedChange(index)
                    sNavController.navigate(item.first.second)
                }
            )
        }
    }
}
enum class StudentScreens {
    HOME, NOTICE, ATTENDANCE, TASKS, ASSISTANT
}
@Immutable
object StudentConst {
    val items = listOf(
        "Home" to StudentScreens.HOME.name to R.drawable.baseline_school_24,
        "Attend" to StudentScreens.ATTENDANCE.name to R.drawable.baseline_data_saver_off_24,
        "Assistant" to StudentScreens.ASSISTANT.name to R.drawable.microchip,
        "Notices" to StudentScreens.NOTICE.name to R.drawable.baseline_dashboard_24,
        "Tasks" to StudentScreens.TASKS.name to R.drawable.baseline_edit_note_24,
    )
}