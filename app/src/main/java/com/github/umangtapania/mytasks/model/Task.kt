package com.github.umangtapania.mytasks.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val priority: TaskPriority,
    val status: TaskStatus,
    val deadline: String
)
