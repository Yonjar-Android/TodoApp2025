package com.example.todoapp.data.mapper

import com.example.todoapp.data.database.entities.CategoryRoomModel
import com.example.todoapp.data.models.CategoryModel
import com.example.todoapp.data.models.CategoryWithTaskCount

object CategoryMapper {
    // Convierte de CategoryModel a CategoryRoomModel
    fun toCategoryRoomModel(categoryModel: CategoryModel): CategoryRoomModel {
        return CategoryRoomModel(
            id = categoryModel.id,
            title = categoryModel.title,
            color = categoryModel.color
        )
    }

    // Convierte de CategoryRoomModel a CategoryModel
    fun toCategoryModel(categoryRoomModel: CategoryRoomModel): CategoryModel {
        return CategoryModel(
            id = categoryRoomModel.id,
            title = categoryRoomModel.title,
            color = categoryRoomModel.color
        )
    }

    fun fromCategoryCountTaskToCategoryModel
                (categoryWithTaskCount:
                 CategoryWithTaskCount):
            CategoryModel {
        return CategoryModel(
            id = categoryWithTaskCount.id,
            title = categoryWithTaskCount.title,
            color = categoryWithTaskCount.color
        )
    }
}