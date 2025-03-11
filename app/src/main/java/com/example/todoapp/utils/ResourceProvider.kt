package com.example.todoapp.utils

import android.content.Context
import com.example.todoapp.di.IResourceProvider

class ResourceProvider(private val context: Context): IResourceProvider {
    override fun getString(stringResId: Int): String {
        return context.getString(stringResId)
    }
}