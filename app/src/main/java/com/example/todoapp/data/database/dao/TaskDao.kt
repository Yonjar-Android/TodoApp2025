package com.example.todoapp.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.todoapp.data.database.entities.TaskRoomModel
import com.example.todoapp.data.models.TaskCompletionPercentage
import com.example.todoapp.data.models.TaskWithCategoryColor
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Insert
    suspend fun createTask(task: TaskRoomModel)

    @Query("SELECT * FROM tasks")
    fun getTasks(): Flow<List<TaskRoomModel>>

    @Update
    suspend fun updateTask(task: TaskRoomModel)

    @Delete
    suspend fun deleteTask(task: TaskRoomModel)

    @Query("DELETE FROM tasks")
    suspend fun deleteAllTasks()

    @Query(
        """
    SELECT t.id, t.title, t.completed, t.categoryId, t.createdDate, 
           t.completedDate, c.color as categoryColor
    FROM tasks t
    INNER JOIN categories c ON t.categoryId = c.id
    WHERE t.createdDate >= :sevenDaysAgo
    ORDER BY t.completed ASC, t.createdDate DESC
"""
    )
    fun getTasksWithCategoryColor(sevenDaysAgo: Long): Flow<List<TaskWithCategoryColor>>

    @Query("""
        SELECT 
            strftime('%Y-%m-%d', completedDate / 1000, 'unixepoch') AS date,
            COUNT(*) AS totalTasks,
            SUM(CASE WHEN completed = 1 THEN 1 ELSE 0 END) AS completedTasks,
            (SUM(CASE WHEN completed = 1 THEN 1 ELSE 0 END) * 100.0 / COUNT(*)) AS completionPercentage
        FROM tasks
        WHERE completedDate IS NOT NULL
          AND completedDate >= :sevenDaysAgo
        GROUP BY date
        ORDER BY date DESC
        LIMIT 7
    """)
    fun getCompletionPercentageLastSevenDays(sevenDaysAgo: Long): Flow<List<TaskCompletionPercentage>>
}
