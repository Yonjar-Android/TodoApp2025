package com.example.todoapp.data.repositories


import com.example.todoapp.data.models.CategoryModel
import com.example.todoapp.data.models.CategoryWithTaskCount
import com.example.todoapp.data.models.TaskModel
import com.example.todoapp.data.models.TaskWithCategoryColor
import kotlinx.coroutines.flow.Flow

interface ITaskRepository {
    fun getTasksColor(): Flow<List<TaskWithCategoryColor>>

    suspend fun createTask(task: TaskModel)

    suspend fun updateTask(task: TaskModel)

    suspend fun deleteTask(task: TaskModel)

    fun getCategoriesCountTask(): Flow<List<CategoryWithTaskCount>>

    suspend fun createCategory(category: CategoryModel)

    suspend fun updateCategory(category: CategoryModel)

    suspend fun deleteCategory(category: CategoryModel)

    fun getCompletionPercentageLastSevenDays(): Flow<List<Double>>
}