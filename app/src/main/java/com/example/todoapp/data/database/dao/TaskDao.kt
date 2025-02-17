package com.example.todoapp.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.todoapp.data.database.entities.TaskRoomModel

@Dao
interface TaskDao {

    @Insert
    suspend fun createTask(task: TaskRoomModel)

    @Query("SELECT * FROM tasks")
    fun getTasks(): List<TaskRoomModel>

}