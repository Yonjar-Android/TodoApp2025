package com.example.todoapp.di

import android.content.Context
import com.example.todoapp.data.database.dao.CategoriesDao
import com.example.todoapp.data.database.dao.TaskDao
import com.example.todoapp.data.repositories.ITaskRepository
import com.example.todoapp.data.repositories.TaskRepository
import com.example.todoapp.utils.ResourceProvider
import com.example.todoapp.utils.clock.Clock
import com.example.todoapp.utils.clock.SystemClock
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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

    @Provides
    fun provideResourceProvider(@ApplicationContext context: Context): ResourceProvider {
        return ResourceProvider(context)
    }

}