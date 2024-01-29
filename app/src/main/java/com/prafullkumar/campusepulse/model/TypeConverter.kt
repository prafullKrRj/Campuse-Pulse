package com.prafullkumar.campusepulse.model

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.prafullkumar.campusepulse.adminApp.models.Branch

// Convert data class to JSON string
fun toJson(newBranch: NewBranch): String {
    val gson = Gson()
    return gson.toJson(newBranch)
}

// Convert JSON string back to data class
fun fromJson(json: String): NewBranch {
    val gson = Gson()
    val type = object : TypeToken<NewBranch>() {}.type
    return gson.fromJson(json, type)
}

data class NewBranch(
    val branchName: String = "",
    val total: Int = 0,
    val timeTable: MutableMap<String, MutableList<ClassDetails>> = mutableMapOf(
        "monday" to mutableListOf(),
        "tuesday" to mutableListOf(),
        "wednesday" to mutableListOf(),
        "thursday" to mutableListOf(),
        "friday" to mutableListOf(),
    ),
    val subjects: List<String> = emptyList(),
    val batches: List<String> = emptyList(),
    val year: String = "",
)
fun convertToBranch(newBranch: NewBranch): Branch {
    val gson = Gson()
    val tt = newBranch.timeTable.mapValues { entry ->
        entry.value.map { classDetails ->
            gson.toJson(classDetails)
        }
    }
    return Branch(
        name = getId(newBranch.year, newBranch.branchName),
        strength = 0,
        subjects = newBranch.subjects.map { it.uppercase() },
        batches = newBranch.batches.map { it.uppercase() },
        tt = tt,
        id = getId(newBranch.year, newBranch.branchName)
    )
}
fun getId(year: String, name: String): String {
    return when (year[0]) {
        '1' -> "fe-${name.uppercase()}"
        '2' -> "se-${name.uppercase()}"
        '3' -> "te-${name.uppercase()}"
        else -> "be-${name.uppercase()}"
    }
}
data class ClassDetails(
    val startTime: String = "",
    val endTime: String = "",
    val type: String = "T",
    val subTeacher: MutableList<Pair<String, String>> = mutableListOf(),
    val lh: String = "",
)
