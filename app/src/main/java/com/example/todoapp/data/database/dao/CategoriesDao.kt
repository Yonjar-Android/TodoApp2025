package com.example.todoapp.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.todoapp.data.database.entities.CategoryRoomModel

@Dao
interface CategoriesDao {
    @Insert
    suspend fun createCategory(category: CategoryRoomModel)

    @Query("SELECT * FROM categories")
    fun getCategories(): List<CategoryRoomModel>
}