package com.example.lunchtray.model

data class TaskData(
    var taskName: String,
    var location : String,
    var tasks: MutableList<String> = mutableListOf<String>()
)