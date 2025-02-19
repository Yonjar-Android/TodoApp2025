package com.example.todoapp.data.models

import androidx.room.PrimaryKey

data class CategoryModel(
    val id: Long = 0,
    val title: String,
    val color: Int
)
