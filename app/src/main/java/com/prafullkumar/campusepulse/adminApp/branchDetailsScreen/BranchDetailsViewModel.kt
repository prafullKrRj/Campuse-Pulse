package com.prafullkumar.campusepulse.adminApp.branchDetailsScreen

import androidx.lifecycle.ViewModel
import com.prafullkumar.campusepulse.data.adminRepos.BranchDetailsRepository

class BranchDetailsViewModel(
    private val branchDetailsRepository: BranchDetailsRepository,
    private val id: String
) : ViewModel() {

    fun getId() = id
}