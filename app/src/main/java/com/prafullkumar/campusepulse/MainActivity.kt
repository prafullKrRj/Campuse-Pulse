package com.prafullkumar.campusepulse

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.prafullkumar.campusepulse.managers.SharedPrefManager
import com.prafullkumar.campusepulse.onBoard.navigationGraph.NavigationGraph
import com.prafullkumar.campusepulse.ui.theme.CampusePulseTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            CampusePulseTheme (darkTheme = true){
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavigationGraph(
                        onBoardViewModel = viewModel(
                            factory = ViewModelProvider.getMainViewModel()
                        )
                    ) {
                        signOutAndExit(this)
                    }
                }
            }
        }
    }
}
fun signOutAndExit(activity: Activity) {
    FirebaseAuth.getInstance().signOut()
    SharedPrefManager(activity).setLoggedIn(false)
    SharedPrefManager(activity).setLoggedInUserType("")
    activity.finishAffinity()
}
fun NavController.goBackStack() {
    if (currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED) {
        popBackStack()
    }
}