package com.example.todoapp.data.repositories

import com.example.todoapp.data.database.dao.CategoriesDao
import com.example.todoapp.data.database.dao.TaskDao
import com.example.todoapp.data.database.entities.CategoryRoomModel
import com.example.todoapp.data.database.entities.TaskRoomModel
import com.example.todoapp.data.mapper.CategoryMapper
import com.example.todoapp.data.mapper.TaskMapper
import com.example.todoapp.data.models.CategoryModel
import com.example.todoapp.data.models.CategoryWithTaskCount
import com.example.todoapp.data.models.TaskModel
import javax.inject.Inject


class TaskRepository @Inject constructor(
    private val taskDao: TaskDao,
    private val categoryDao: CategoriesDao
) {

    suspend fun getTasks(): List<TaskModel> {

        val tasks:List<TaskRoomModel> = taskDao.getTasks()
        val tasksGet = mutableListOf<TaskModel>()

        if (tasks.isNotEmpty()){
            for (task in tasks){
                tasksGet.add(TaskMapper.toTaskModel(task))

            }
        }
        return tasksGet
    }

    suspend fun createTask(task: TaskModel) {
        val taskToCreate = TaskMapper.toTaskRoomModel(task)

        taskDao.createTask(taskToCreate)
    }

    suspend fun updateTask(task:TaskModel){
        val taskUpdated = TaskMapper.toTaskRoomModel(task)
        taskDao.updateTask(taskUpdated)
    }

    suspend fun deleteTask(task: TaskModel){
        val taskToDelete = TaskMapper.toTaskRoomModel(task)
        taskDao.deleteTask(taskToDelete)
    }

    suspend fun getCategories(): List<CategoryModel> {

        val categories:List<CategoryRoomModel> = categoryDao.getCategories()
        val categoriesGet = mutableListOf<CategoryModel>()

        if (categories.isNotEmpty()){
            for (category in categories){
                categoriesGet.add(CategoryMapper.toCategoryModel(category))

            }
        }
        return categoriesGet
    }

    suspend fun getCategoriesCountTask(): List<CategoryWithTaskCount> {
        val categories:List<CategoryWithTaskCount> = categoryDao.getTaskCountByCategory()

        return categories
    }

    suspend fun createCategory(category: CategoryModel) {
        val categoryToCreate = CategoryMapper.toCategoryRoomModel(category)

        categoryDao.createCategory(categoryToCreate)
    }

    suspend fun updateCategory(category:CategoryModel){
        val categoryUpdated = CategoryMapper.toCategoryRoomModel(category)
        categoryDao.updateCategory(categoryUpdated)
    }

    suspend fun deleteCategory(category: CategoryModel){
        val categoryToDelete = CategoryMapper.toCategoryRoomModel(category)
        categoryDao.deleteCategory(categoryToDelete)
    }

}