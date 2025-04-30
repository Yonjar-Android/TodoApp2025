package com.example.todoapp.data.models

data class CategoryWithTaskCount(
    val id: Long,
    val title: String,
    val color: Int,
    val totalTasks: Int,
    val pendingTasks: Int,
    val completedTasks: Int
)