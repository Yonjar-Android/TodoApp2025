package com.example.todoapp.di

import com.example.todoapp.data.database.dao.CategoriesDao
import com.example.todoapp.data.database.dao.TaskDao
import com.example.todoapp.data.repositories.ITaskRepository
import com.example.todoapp.data.repositories.TaskRepository
import com.example.todoapp.utils.clock.Clock
import com.example.todoapp.utils.clock.SystemClock
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
        categoryDao: CategoriesDao,
        clock: Clock
    ): ITaskRepository {
        return TaskRepository(taskDao, categoryDao, clock)
    }

    @Singleton
    @Provides
    fun provideSystemClock(): Clock {
        return SystemClock()
    }


}