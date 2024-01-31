package com.prafullkumar.campusepulse.adminApp.domain.models

import android.net.Uri

data class NewBranch(
    val branchName: String = "",
    val total: Int = 0,
    val subjects: List<String> = emptyList(),
    val batches: List<String> = emptyList(),
    val year: String = "",
    val timeTable: Uri? = null,
)
