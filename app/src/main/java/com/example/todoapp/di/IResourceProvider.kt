package com.example.todoapp.di

interface IResourceProvider {
    fun getString(stringResId: Int): String
}