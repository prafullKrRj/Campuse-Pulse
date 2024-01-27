package com.prafullkumar.campusepulse.adminApp.models

data class Branch(
    val id: String? = "",
    val name: String? = "",
    val strength: Int? = 0,
    val tt: Map<String, List<String>>? = mapOf(),
    val batches: List<String>? = listOf(),
    val subjects: List<String>
)

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