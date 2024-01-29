package com.prafullkumar.campusepulse.studentApp.homeScreen.components

import androidx.compose.ui.unit.dp
import androidx.compose.material3.Card
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration

@Composable
fun TimeTableUI(timeTable: List<String>) {
    Column(
        Modifier.fillMaxSize()
            .heightIn(min = LocalConfiguration.current.screenHeightDp.dp)
    ) {
        timeTable.forEach { slotDetails ->
            TimeTableSlot(slotDetails)
        }
    }
}

@Composable
private fun TimeTableSlot(slotDetails: String) {
    val details = slotDetails.split("-")
    Row(
        Modifier.fillMaxWidth()
    ) {
        TimeCard(modifier = Modifier.weight(.35f), startTime = details[0], endTime = details[1])
        SubjectCard(modifier = Modifier.weight(.65f), subject = details[2], teacher = details[3], room = "LH 09")
    }
}
@Composable
private fun SubjectCard(
    modifier: Modifier = Modifier,
    subject: String,
    teacher: String,
    room: String = "LH 09",
) {
    Card(modifier = modifier.padding(horizontal = 8.dp, vertical = 8.dp)) {
        Column(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp)
        ) {
            Text(text = subject)
            Text(text = teacher)
            Text(text = room)
        }
    }
}
@Composable
private fun TimeCard(modifier: Modifier = Modifier, startTime: String = "8:45 AM", endTime: String = "9:45 AM") {
    Card(modifier = modifier.padding(vertical = 8.dp).padding(start = 8.dp)) {
        Column(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp)
        ) {
            Text(text = "$startTime - $endTime")
        }
    }
}