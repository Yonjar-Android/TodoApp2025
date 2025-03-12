package com.example.todoapp.data.models

data class TaskCompletionPercentage(
    val date: String,
    val totalTasks: Int,
    val completedTasks: Int,
    val completionPercentage: Double
)