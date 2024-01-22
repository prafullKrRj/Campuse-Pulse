package com.prafullkumar.campusepulse.adminApp.addBranchScreen

import androidx.compose.material3.rememberTimePickerState
import androidx.compose.material3.TimeInput
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextButton
import androidx.compose.material3.AlertDialog
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.prafullkumar.campusepulse.adminApp.homeScreen.AdminViewModel
import com.prafullkumar.campusepulse.model.Day
import com.prafullkumar.campusepulse.model.NewBranch
import com.prafullkumar.campusepulse.model.Slot
@Composable
fun AddTimeTableComposable(vm: AdminViewModel) {
    val newBranch by vm.newBranch.collectAsState()
    Column(
        Modifier.fillMaxSize()
    ) {
        DayRow(day = "Monday", vm = vm, newBranch = newBranch)
        DayDivider()
        DayRow(day = "Tuesday", vm = vm, newBranch = newBranch)
        DayDivider()
        DayRow(day = "Wednesday", vm = vm, newBranch = newBranch)
        DayDivider()
        DayRow(day = "Thursday", vm = vm, newBranch = newBranch)
        DayDivider()
        DayRow(day = "Friday", vm = vm, newBranch = newBranch)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DayRow(day: String, vm: AdminViewModel, newBranch: NewBranch) {
    val slots = newBranch.timeTable.monday.slots
    var openDialog by remember { mutableStateOf(false) }
    var lastTime by rememberSaveable {
        mutableStateOf("")
    }
    Row(
        Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Box(modifier = Modifier.weight(.25f), contentAlignment = Alignment.Center) {
            Text(text = day, textAlign = TextAlign.Start)
        }
        LazyRow(
            modifier = Modifier
                .weight(.75f)
                .padding(8.dp)
        ) {
            items(slots.size) { index ->
                val slot = slots[index]
                SlotRow(slot = slot)
            }
            item {
                FilledIconButton(
                    onClick = {
                        openDialog = true
                    }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Add,
                        contentDescription = "Add"
                    )
                }
            }
        }
    }
    if (openDialog) {
        AddSlotDialog(lastTime = lastTime, openDialog = {
            openDialog = it
        }, vm = vm)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddSlotDialog(lastTime: String, openDialog: (Boolean) -> Unit, vm: AdminViewModel) {
    var selectFromTime by rememberSaveable {
        mutableStateOf(false)
    }
    var selectToTime by rememberSaveable {
        mutableStateOf(false)
    }
    val state = rememberTimePickerState()
    AlertDialog(
        onDismissRequest = {  },
        title = {
            Text(text = "Add Slot")
        },
        text = {
            Column {
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Column {
                        TextButton(
                            onClick = {selectFromTime = true},
                            enabled = lastTime.isEmpty()
                        ) {
                            Text(text = lastTime.ifEmpty { "From" })
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        if (selectFromTime) {
                            TimeInput(state = state)
                        }
                    }
                    Text(text = "-")
                    Column(modifier = Modifier) {
                        TextButton(onClick = {
                            selectToTime = true
                        }) {
                            Text(text = "To")
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    openDialog(false)
                }
            ) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    openDialog(false)
                }
            ) {
                Text("Dismiss")
            }
        }
    )
}
@Composable
fun SlotRow(slot: Slot) {
    Card {
        Text(text = "${slot.from} - ${slot.to}", modifier = Modifier.padding(8.dp))
        Text(text = slot.subject, modifier = Modifier.padding(8.dp))
    }
}

@Composable
fun DayDivider() {
    Divider(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    )
}
fun getDayName(day: Day): String {
    return when (day) {
        is Day.Monday -> "Monday"
        is Day.Tuesday -> "Tuesday"
        is Day.Wednesday -> "Wednesday"
        is Day.Thursday -> "Thursday"
        is Day.Friday -> "Friday"
    }
}