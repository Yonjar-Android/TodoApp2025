package com.example.todoapp.data.models

data class TaskWithCategoryColor(
    val id: Long,
    val title: String,
    val completed: Boolean,
    val categoryId: Long,
    val createdDate: Long,
    val completedDate: Long,
    val categoryColor: Int // Nuevo campo
)