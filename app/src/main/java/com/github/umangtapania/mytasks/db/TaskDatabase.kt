package com.github.umangtapania.mytasks.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.github.umangtapania.mytasks.model.Task

@Database(entities = [Task::class], version = 1)
@TypeConverters(TaskFieldConverters::class)
abstract class TaskDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
}