package com.example.todoapp

import com.example.todoapp.di.IResourceProvider

class FakeResourceProvider : IResourceProvider {
    override fun getString(stringResId: Int): String {
        return when (stringResId) {
            R.string.newTaskCreatedMsg -> "Task created successfully"
            R.string.newCategoryCreatedMsg -> "Category created successfully"
            else -> throw IllegalArgumentException("String resource ID not found")
        }
    }
}