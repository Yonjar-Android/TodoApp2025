package com.example.todoapp.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.todoapp.data.database.entities.TaskRoomModel
import com.example.todoapp.data.models.TaskWithCategoryColor

@Dao
interface TaskDao {

    @Insert
    suspend fun createTask(task: TaskRoomModel)

    @Query("SELECT * FROM tasks")
    suspend fun getTasks(): List<TaskRoomModel>

    @Update
    suspend fun updateTask(task: TaskRoomModel)

    @Delete
    suspend fun deleteTask(task: TaskRoomModel)

    @Query("DELETE FROM tasks")
    suspend fun deleteAllTasks()

    @Query("""
        SELECT t.id, t.title, t.completed, t.categoryId, t.createdDate, 
               t.completedDate, c.color as categoryColor
        FROM tasks t
        INNER JOIN categories c ON t.categoryId = c.id
    """)
    suspend fun getTasksWithCategoryColor(): List<TaskWithCategoryColor>
}