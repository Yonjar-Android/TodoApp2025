package com.example.todoapp.data.mapper

import com.example.todoapp.data.database.entities.TaskRoomModel
import com.example.todoapp.data.models.TaskModel
import com.example.todoapp.data.models.TaskWithCategoryColor

object TaskMapper {
    // Convierte de TaskModel a TaskRoomModel
    fun toTaskRoomModel(taskModel: TaskModel): TaskRoomModel {
        return TaskRoomModel(
            id = taskModel.id,
            title = taskModel.title,
            completed = taskModel.completed,
            categoryId = taskModel.categoryId,
            createdDate = taskModel.createdDate,
            completedDate = taskModel.completedDate
        )
    }

    // Convierte de TaskRoomModel a TaskModel
    fun toTaskModel(taskRoomModel: TaskRoomModel): TaskModel {
        return TaskModel(
            id = taskRoomModel.id,
            title = taskRoomModel.title,
            completed = taskRoomModel.completed,
            categoryId = taskRoomModel.categoryId,
            createdDate = taskRoomModel.createdDate,
            completedDate = taskRoomModel.completedDate
        )
    }

    fun toTaskModelFromTaskColor(taskWithCategoryColor: TaskWithCategoryColor): TaskModel{
        return TaskModel(
            id = taskWithCategoryColor.id,
            categoryId = taskWithCategoryColor.categoryId,
            createdDate = taskWithCategoryColor.createdDate,
            completedDate = taskWithCategoryColor.completedDate,
            completed = taskWithCategoryColor.completed,
            title = taskWithCategoryColor.title
        )
    }
}