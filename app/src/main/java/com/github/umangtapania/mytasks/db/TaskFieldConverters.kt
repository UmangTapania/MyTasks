package com.github.umangtapania.mytasks.db

import androidx.room.TypeConverter
import com.github.umangtapania.mytasks.model.TaskPriority
import com.github.umangtapania.mytasks.model.TaskStatus

class TaskFieldConverters {

    @TypeConverter
    fun fromTaskPriority(priority: TaskPriority): String = priority.name

    @TypeConverter
    fun toTaskPriority(name: String): TaskPriority = TaskPriority.valueOf(name)

    @TypeConverter
    fun fromTaskStatus(status: TaskStatus): String = status.name

    @TypeConverter
    fun toTaskStatus(name: String): TaskStatus = TaskStatus.valueOf(name)
}