package com.prafullkumar.campusepulse.adminApp.models

data class Branch(
    val id: String? = "",
    val name: String? = "",
    val strength: Int? = 0,
    val tt: Map<String, List<String>>? = mapOf(),
)

data class Student(

    val fName: String? = "",
    val lName: String? = "",
    val phone: Long? = 0,
    val branch: String? = "",
    val rollNo: Long? = 0,
    val admissionNo: Long? = 0,
    val dob: String? = "",
    val batch: String? = "",
    val attendance: Map<String, List<Long>>? = mapOf()
)