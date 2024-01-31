package com.prafullkumar.campusepulse.studentApp.ui.noticeScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.prafullkumar.campusepulse.R
import com.prafullkumar.campusepulse.commons.ErrorScreen
import com.prafullkumar.campusepulse.commons.LoadingScreen
import com.prafullkumar.campusepulse.commons.TopAppBar

@Composable
fun NoticeScreen(viewModel: NoticeViewModel) {
    val state by viewModel.state.collectAsState()
    Scaffold(
        topBar = {
           TopAppBar(
               label = R.string.notice,
           )
        }
    ) { paddingValues ->
        when (state) {
            is NoticeState.Loading -> {
                LoadingScreen()
            }
            is NoticeState.Success -> {
                val data = (state as NoticeState.Success).data
                LazyColumn(
                    contentPadding = paddingValues
                ) {
                    items(data.size) { index ->
                        NoticeItem(
                            index = index + 1,
                            url = formatText(data[index]),
                            onClick = {
                                viewModel.shareLink(data[index])
                            }
                        )
                    }
                }
            }
            is NoticeState.Error -> {
                ErrorScreen {
                    viewModel.getNotices()
                }
            }
        }
    }
}


@Composable
fun NoticeItem(index: Int, url: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(PaddingValues(horizontal = 16.dp, vertical = 6.dp))
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Row(modifier = Modifier.padding(PaddingValues(16.dp))) {
            Text(text = "$index. ")
            Text(text = url)
        }
    }
}
fun formatText(text: String): String {
    return text.substringAfter("Notices/").substringBefore(".pdf")
}