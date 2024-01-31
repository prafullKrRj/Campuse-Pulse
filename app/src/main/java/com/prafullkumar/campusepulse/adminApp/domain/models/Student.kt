package com.prafullkumar.campusepulse.adminApp.domain.models

data class Student(
    val fname: String? = "",
    val lname: String? = "",
    val phoneNo: Long? = 0,
    val branch: String? = "",
    val rollNo: Long? = 0,
    val admNo: Long? = 0,
    val dob: String? = "",
    val batch: String? = "",
    val attendance: Map<String, List<Long>>? = mapOf(),
)