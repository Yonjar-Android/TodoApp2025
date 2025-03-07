package com.example.todoapp.di

import com.example.todoapp.data.database.dao.CategoriesDao
import com.example.todoapp.data.database.dao.TaskDao
import com.example.todoapp.data.repositories.ITaskRepository
import com.example.todoapp.data.repositories.TaskRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideRepository(
        taskDao: TaskDao,
        categoryDao: CategoriesDao
    ): ITaskRepository {
        return TaskRepository(taskDao, categoryDao)
    }

}