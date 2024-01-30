package com.prafullkumar.campusepulse.model

import android.graphics.Bitmap
import android.net.Uri
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
    val subjects: List<String> = emptyList(),
    val batches: List<String> = emptyList(),
    val year: String = "",
    val timeTable: Uri? = null,
)
fun convertToBranch(newBranch: NewBranch): Branch {
    val gson = Gson()
    return Branch(
        name = getId(newBranch.year, newBranch.branchName),
        strength = 0,
        subjects = newBranch.subjects.map { it.uppercase() },
        batches = newBranch.batches.map { it.uppercase() },
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
