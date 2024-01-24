package com.prafullkumar.campusepulse.studentApp.assistant

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import com.prafullkumar.campusepulse.R
import com.prafullkumar.campusepulse.commons.TopAppBar
import kotlinx.coroutines.launch


@Composable
fun AssistantScreen(viewModel: AssistantsViewModel) {
    Scaffold(
        topBar = {
            TopAppBar(
                label = R.string.assistants,
            )
        }
    ) { innerPadding ->
        AssistantsMainUI(innerPadding, viewModel = viewModel)
    }
}
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AssistantsMainUI(paddingValues: PaddingValues, viewModel: AssistantsViewModel) {
    Column(
        modifier = Modifier.padding(paddingValues),
    ) {
        val tabs = remember { listOf("AI", "Syllabus") }
        val pagerState = rememberPagerState {
            tabs.size
        }
        val currentTabIndex by remember { derivedStateOf { pagerState.currentPage } }

        TabRow(
            modifier = Modifier,
            selectedTabIndex = currentTabIndex,
        ) {
            tabs.forEachIndexed { index, title ->
                val coroutineScope = rememberCoroutineScope()
                Tab(
                    selected = currentTabIndex == index,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                    text = {
                        Text(
                            text = title,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                )
            }
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize(),
            pageSize = PageSize.Fill,
        ) { tabIndex ->
            when (tabIndex) {
                0 -> {
                    AiAssistantScreen(viewModel = viewModel)
                }
                1 -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Syllabus",
                            style = MaterialTheme.typography.headlineLarge,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}