package com.example.todoapp.data.repositories

import com.example.todoapp.data.database.dao.CategoriesDao
import com.example.todoapp.data.database.dao.TaskDao
import com.example.todoapp.data.database.entities.TaskRoomModel
import javax.inject.Inject


class TaskRepository @Inject constructor(
    private val taskDao: TaskDao,
    private val categoryDao: CategoriesDao
) {
    suspend fun createTask(task: TaskRoomModel) {
        taskDao.createTask(task)
    }

    fun getTasks(): List<TaskRoomModel> {
        return taskDao.getTasks()
    }
}