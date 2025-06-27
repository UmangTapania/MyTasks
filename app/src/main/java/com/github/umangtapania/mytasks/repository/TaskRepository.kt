package com.github.umangtapania.mytasks.repository

import com.github.umangtapania.mytasks.db.TaskDao
import com.github.umangtapania.mytasks.model.Task
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TaskRepository @Inject constructor(private val dao: TaskDao) {
    val allTasks: Flow<List<Task>> = dao.getAllTasks()

    suspend fun insertTask(task: Task) {
        dao.insertTask(task)
    }

    suspend fun updateTask(task: Task) {
        dao.updateTask(task)
    }

    suspend fun deleteTask(task: Task) {
        dao.deleteTask(task)
    }

    suspend fun getTaskById(id: Int): Task? {
        return dao.getTaskById(id)
    }
}