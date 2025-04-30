package com.example.todoapp.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories")
data class CategoryRoomModel(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val color: Int
)
