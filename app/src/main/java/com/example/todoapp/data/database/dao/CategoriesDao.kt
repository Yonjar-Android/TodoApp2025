package com.example.todoapp.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.todoapp.data.database.entities.CategoryRoomModel
import com.example.todoapp.data.models.CategoryWithTaskCount

@Dao
interface CategoriesDao {
    @Insert
    suspend fun createCategory(category: CategoryRoomModel)

    @Query("SELECT * FROM categories")
    suspend fun getCategories(): List<CategoryRoomModel>

    @Update
    suspend fun updateCategory(category: CategoryRoomModel)

    @Delete
    suspend fun deleteCategory(category: CategoryRoomModel)

    @Query("DELETE FROM categories")
    suspend fun deleteAllCategories()

    @Query("""
    SELECT 
        categories.id, 
        categories.title, 
        categories.color, 
        COUNT(tasks.id) AS totalTasks, 
        SUM(CASE WHEN tasks.completed = 0 THEN 1 ELSE 0 END) AS pendingTasks, 
        SUM(CASE WHEN tasks.completed = 1 THEN 1 ELSE 0 END) AS completedTasks
    FROM categories 
    LEFT JOIN tasks ON categories.id = tasks.categoryId 
    GROUP BY categories.id, categories.title, categories.color
""")
    suspend fun getTaskCountByCategory(): List<CategoryWithTaskCount>
}