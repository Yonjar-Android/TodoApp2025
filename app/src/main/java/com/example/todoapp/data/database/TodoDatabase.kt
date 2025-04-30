package com.example.todoapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.todoapp.data.database.dao.CategoriesDao
import com.example.todoapp.data.database.dao.TaskDao
import com.example.todoapp.data.database.entities.CategoryRoomModel
import com.example.todoapp.data.database.entities.TaskRoomModel

@Database(
    entities = [TaskRoomModel::class, CategoryRoomModel::class],
    version = 1
)
abstract class TodoDatabase: RoomDatabase() {
    abstract fun taskDao(): TaskDao
    abstract fun categoryDao(): CategoriesDao
}