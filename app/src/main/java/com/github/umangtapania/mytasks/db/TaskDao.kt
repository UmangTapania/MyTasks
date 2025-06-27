package com.github.umangtapania.mytasks.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.github.umangtapania.mytasks.model.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Query("SELECT * FROM tasks")
    fun getAllTasks(): Flow<List<Task>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: Task)

    @Update
    suspend fun updateTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)

    @Query("SELECT * FROM tasks WHERE id = :id LIMIT 1")
    suspend fun getTaskById(id: Int): Task?

    @Query("SELECT * FROM tasks WHERE priority = 'HIGH' AND status != 'COMPLETED'")
    suspend fun getHighPriorityPendingTasks(): List<Task>

    @Query("SELECT * FROM tasks WHERE status != 'COMPLETED'")
    suspend fun getAllUncompletedTasks(): List<Task>
}