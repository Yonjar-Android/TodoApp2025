package com.example.todoapp

import com.example.todoapp.data.models.CategoryModel
import com.example.todoapp.data.models.CategoryWithTaskCount
import com.example.todoapp.data.models.TaskModel
import com.example.todoapp.data.models.TaskWithCategoryColor
import com.example.todoapp.data.repositories.ITaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeRepository: ITaskRepository {

    private val tasksTest = MotherObjectTestUi.listOfTasks

    private val tasksToAdd = MotherObjectTestUi.tasksToAdd

    private val categoriesTest = MotherObjectTestUi.listOfCategories

    private val categoriesToAdd = MotherObjectTestUi.categoriesToAdd


    override fun getTasksColor(): Flow<List<TaskWithCategoryColor>> {
        return flowOf(tasksTest)
    }

    override suspend fun createTask(task: TaskModel) {
        tasksToAdd.add(task)
    }

    override suspend fun updateTask(task: TaskModel) {
        val index = tasksToAdd.indexOfFirst { it.id == task.id }

        tasksToAdd[index] = task
    }

    override suspend fun deleteTask(task: TaskModel) {
        tasksToAdd.remove(task)
    }

    override fun getCategoriesCountTask(): Flow<List<CategoryWithTaskCount>> {
        return flowOf(categoriesTest)
    }

    override suspend fun createCategory(category: CategoryModel) {
        categoriesToAdd.add(category)
    }

    override suspend fun updateCategory(category: CategoryModel) {
        val index = categoriesToAdd.indexOfFirst { it.id == category.id }

        categoriesToAdd[index] = category
    }

    override suspend fun deleteCategory(category: CategoryModel) {
        categoriesToAdd.remove(category)
    }

}