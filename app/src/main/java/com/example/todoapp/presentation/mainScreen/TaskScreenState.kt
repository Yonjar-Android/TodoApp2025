package com.example.todoapp.presentation.mainScreen

import com.example.todoapp.data.models.CategoryModel
import com.example.todoapp.data.models.CategoryWithTaskCount
import com.example.todoapp.data.models.TaskModel

data class TaskScreenState(
    val tasks:List<TaskModel> = mutableListOf(),
    val categories:List<CategoryWithTaskCount> = mutableListOf(),
    val isLoading:Boolean = false,
    val message:String? = null
)