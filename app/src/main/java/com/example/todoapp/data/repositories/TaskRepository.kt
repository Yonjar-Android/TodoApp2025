package com.example.todoapp.data.repositories

import com.example.todoapp.data.database.dao.CategoriesDao
import com.example.todoapp.data.database.dao.TaskDao
import com.example.todoapp.data.mapper.CategoryMapper
import com.example.todoapp.data.mapper.TaskMapper
import com.example.todoapp.data.models.CategoryModel
import com.example.todoapp.data.models.CategoryWithTaskCount
import com.example.todoapp.data.models.TaskModel
import com.example.todoapp.data.models.TaskWithCategoryColor
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class TaskRepository @Inject constructor(
    private val taskDao: TaskDao,
    private val categoryDao: CategoriesDao
) {

    fun getTasksColor(): Flow<List<TaskWithCategoryColor>> {
        return taskDao.getTasksWithCategoryColor()
    }

    suspend fun createTask(task: TaskModel) {
        val taskToCreate = TaskMapper.toTaskRoomModel(task)

        taskDao.createTask(taskToCreate)
    }

    suspend fun updateTask(task: TaskModel) {
        val taskUpdated = TaskMapper.toTaskRoomModel(task)
        taskDao.updateTask(taskUpdated)
    }

    suspend fun deleteTask(task: TaskModel) {
        val taskToDelete = TaskMapper.toTaskRoomModel(task)
        taskDao.deleteTask(taskToDelete)
    }

    fun getCategoriesCountTask(): Flow<List<CategoryWithTaskCount>> {
        return categoryDao.getTaskCountByCategory()
    }

    suspend fun createCategory(category: CategoryModel) {
        val categoryToCreate = CategoryMapper.toCategoryRoomModel(category)

        categoryDao.createCategory(categoryToCreate)
    }

    suspend fun updateCategory(category: CategoryModel) {
        val categoryUpdated = CategoryMapper.toCategoryRoomModel(category)
        categoryDao.updateCategory(categoryUpdated)
    }

    suspend fun deleteCategory(category: CategoryModel) {
        val categoryToDelete = CategoryMapper.toCategoryRoomModel(category)
        categoryDao.deleteCategory(categoryToDelete)

    }
}