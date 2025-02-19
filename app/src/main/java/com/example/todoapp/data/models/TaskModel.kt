package com.example.todoapp.data.models

import androidx.room.PrimaryKey

data class TaskModel(
val id: Long = 0,
val title: String,
val completed: Boolean = false,
val categoryId: Long,
val createdDate: Long,
val completedDate: Long
)
