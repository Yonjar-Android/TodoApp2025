package com.example.todoapp.data.database.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "tasks",
    foreignKeys = [
        ForeignKey(
            entity = CategoryRoomModel::class,
            parentColumns = ["id"],
            childColumns = ["categoryId"],
            onDelete = ForeignKey.CASCADE // Opcional: Elimina tareas si se elimina la categoría
        )
    ]
)
data class TaskRoomModel(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val completed: Boolean = false,
    val categoryId: Long, // Clave foránea
    val createdDate: Long, // Timestamp
    val completedDate: Long // Timestamp
)
