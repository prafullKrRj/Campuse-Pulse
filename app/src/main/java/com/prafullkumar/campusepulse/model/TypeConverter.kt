package com.prafullkumar.campusepulse.model

import com.google.gson.Gson


fun fromString(value: String): List<NewBranch> {
    val listType = object : com.google.gson.reflect.TypeToken<List<NewBranch>>() {}.type
    return Gson().fromJson(value, listType)
}
fun fromBranchList(branchList: List<NewBranch>): String {
    return Gson().toJson(branchList)
}

data class NewBranch(
    val branchName: String = "",
    val total: Int = 0,
    val timeTable: TimeTable = TimeTable(
        monday = Day.Monday(emptyList()),
        tuesday = Day.Tuesday(emptyList()),
        wednesday = Day.Wednesday(emptyList()),
        thursday = Day.Thursday(emptyList()),
        friday = Day.Friday(emptyList())
    ),
    val subjects: List<String> = emptyList(),
    val batches: List<String> = emptyList(),
    val year: String = "",
)
data class TimeTable(
    val monday: Day.Monday,
    val tuesday: Day.Tuesday,
    val wednesday: Day.Wednesday,
    val thursday: Day.Thursday,
    val friday: Day.Friday
)

sealed class Day {
    data class Monday(val slots: List<Slot>): Day()
    data class Tuesday(val slots: List<Slot>): Day()
    data class Wednesday(val slots: List<Slot>): Day()
    data class Thursday(val slots: List<Slot>): Day()
    data class Friday(val slots: List<Slot>): Day()
}
data class Slot(
    val from: String,
    val to: String,
    val type: String,
    val subject: String,
    val faculty: String,
)
