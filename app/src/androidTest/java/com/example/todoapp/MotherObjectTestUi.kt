package com.example.todoapp

import com.example.todoapp.data.models.CategoryModel
import com.example.todoapp.data.models.CategoryWithTaskCount
import com.example.todoapp.data.models.TaskModel
import com.example.todoapp.data.models.TaskWithCategoryColor

object MotherObjectTestUi {
    val listOfTasks = mutableListOf(
        TaskWithCategoryColor(
            id = 1L,
            title = "Comprar carne",
            createdDate = System.currentTimeMillis(),
            completedDate = 0L,
            completed = false,
            categoryId = 1L,
            categoryColor = 999999
        ),
        TaskWithCategoryColor(
            id = 2L,
            title = "Comprar queso",
            createdDate = System.currentTimeMillis(),
            completedDate = 0L,
            completed = false,
            categoryId = 2L,
            categoryColor = 666666
        )
    )

    val tasksToAdd = mutableListOf(
        TaskModel(
            id = 1L,
            title = "Comprar leche",
            categoryId = 1L,
            completed = false,
            createdDate = System.currentTimeMillis(),
            completedDate = 0L
        ),
        TaskModel(
            id = 1L,
            title = "Comprar queso",
            categoryId = 1L,
            completed = false,
            createdDate = System.currentTimeMillis(),
            completedDate = 0L
        )
    )

    val listOfCategories = listOf(
        CategoryWithTaskCount(
            id = 1L,
            title = "Finanzas",
            color = 999999,
            completedTasks = 5,
            pendingTasks = 5,
            totalTasks = 10
        )
    )

    val categoriesToAdd = mutableListOf<CategoryModel>()


}