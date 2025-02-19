package com.example.todoapp.data.repositories

import com.example.todoapp.data.database.dao.CategoriesDao
import com.example.todoapp.data.database.dao.TaskDao
import com.example.todoapp.data.database.entities.CategoryRoomModel
import com.example.todoapp.data.database.entities.TaskRoomModel
import com.example.todoapp.data.mapper.CategoryMapper
import com.example.todoapp.data.mapper.TaskMapper
import com.example.todoapp.data.models.CategoryModel
import com.example.todoapp.data.models.TaskModel
import javax.inject.Inject


class TaskRepository @Inject constructor(
    private val taskDao: TaskDao,
    private val categoryDao: CategoriesDao
) {
    suspend fun createTask(task: TaskModel) {
        val taskToCreate = TaskMapper.toTaskRoomModel(task)

        taskDao.createTask(taskToCreate)
    }

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

    suspend fun createCategory(category: CategoryModel) {
        val categoryToCreate = CategoryMapper.toCategoryRoomModel(category)

        categoryDao.createCategory(categoryToCreate)
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
}