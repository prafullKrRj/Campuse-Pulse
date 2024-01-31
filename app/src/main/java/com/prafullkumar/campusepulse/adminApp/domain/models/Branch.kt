package com.prafullkumar.campusepulse.adminApp.domain.models

data class Branch(
    val id: String? = "",
    val name: String? = "",
    val strength: Long? = 0,
    val batches: List<String>? = listOf(),
    val subjects: List<String> = listOf(),
    var timeTable: String? = null
)
