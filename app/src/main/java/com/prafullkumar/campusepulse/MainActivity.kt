package com.prafullkumar.campusepulse

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.prafullkumar.campusepulse.managers.SharedPrefManager
import com.prafullkumar.campusepulse.managers.ViewModelProvider
import com.prafullkumar.campusepulse.presentations.navigationGraph.NavigationGraph
import com.prafullkumar.campusepulse.ui.theme.CampusePulseTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CampusePulseTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavigationGraph(
                        onBoardViewModel = viewModel(
                            factory = ViewModelProvider.getMainViewModel()
                        )
                    )
                }
            }
        }
    }
}