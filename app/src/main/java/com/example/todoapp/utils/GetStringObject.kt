package com.example.todoapp.utils

import android.content.Context

object GetStringObject {
    fun getStringResource(context: Context, stringResId: Int): String {
        return context.getString(stringResId)
    }
}