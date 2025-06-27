package com.github.umangtapania.mytasks.dependency_injection

import android.content.Context
import androidx.room.Room
import com.github.umangtapania.mytasks.db.TaskDao
import com.github.umangtapania.mytasks.db.TaskDatabase
import com.github.umangtapania.mytasks.repository.TaskRepository
import com.github.umangtapania.mytasks.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideTaskDao(database: TaskDatabase): TaskDao = database.taskDao()

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): TaskDatabase =
        Room.databaseBuilder(
            context,
            TaskDatabase::class.java,
            Constants.TASK_DB
        ).build()

    @Provides
    fun provideRepository(taskDao: TaskDao): TaskRepository = TaskRepository(taskDao)
}
