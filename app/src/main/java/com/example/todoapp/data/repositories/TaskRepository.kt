package com.example.todoapp.data.repositories

import com.example.todoapp.data.database.dao.CategoriesDao
import com.example.todoapp.data.database.dao.TaskDao
import com.example.todoapp.data.mapper.CategoryMapper
import com.example.todoapp.data.mapper.TaskMapper
import com.example.todoapp.data.models.CategoryModel
import com.example.todoapp.data.models.CategoryWithTaskCount
import com.example.todoapp.data.models.TaskModel
import com.example.todoapp.data.models.TaskWithCategoryColor
import com.example.todoapp.utils.clock.Clock
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class TaskRepository @Inject constructor(
    private val taskDao: TaskDao,
    private val categoryDao: CategoriesDao,
    private val clock: Clock
) : ITaskRepository {

    override fun getTasksColor(): Flow<List<TaskWithCategoryColor>> {
        val sevenDaysAgo = clock.currentTimeMillis() - (7 * 24 * 60 * 60 * 1000)
        return taskDao.getTasksWithCategoryColor(sevenDaysAgo)
    }

    override suspend fun createTask(task: TaskModel) {
        val taskToCreate = TaskMapper.toTaskRoomModel(task)

        taskDao.createTask(taskToCreate)
    }

    override suspend fun updateTask(task: TaskModel) {
        val taskUpdated = TaskMapper.toTaskRoomModel(task)
        taskDao.updateTask(taskUpdated)
    }

    override suspend fun deleteTask(task: TaskModel) {
        val taskToDelete = TaskMapper.toTaskRoomModel(task)
        taskDao.deleteTask(taskToDelete)
    }

    override fun getCategoriesCountTask(): Flow<List<CategoryWithTaskCount>> {
        return categoryDao.getTaskCountByCategory()
    }

    override suspend fun createCategory(category: CategoryModel) {
        val categoryToCreate = CategoryMapper.toCategoryRoomModel(category)

        categoryDao.createCategory(categoryToCreate)
    }

    override suspend fun updateCategory(category: CategoryModel) {
        val categoryUpdated = CategoryMapper.toCategoryRoomModel(category)
        categoryDao.updateCategory(categoryUpdated)
    }

    override suspend fun deleteCategory(category: CategoryModel) {
        val categoryToDelete = CategoryMapper.toCategoryRoomModel(category)
        categoryDao.deleteCategory(categoryToDelete)

    }

    override fun getCompletionPercentageLastSevenDays(): Flow<List<Double>> {
        val sevenDaysAgo = clock.currentTimeMillis() - (7 * 24 * 60 * 60 * 1000)
        return taskDao.getCompletionPercentageLastSevenDays(sevenDaysAgo)
            .map { list ->
                list.map { it.completionPercentage }
            }
    }
}
