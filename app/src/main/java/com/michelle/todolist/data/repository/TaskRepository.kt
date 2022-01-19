package com.michelle.todolist.data.repository

import androidx.lifecycle.LiveData
import com.michelle.todolist.data.db.entity.TaskEntity

interface TaskRepository {
    suspend fun insertTask(title: String, description: String): Long

    suspend fun updateTask(id: Long, title: String, description: String)

    suspend fun deleteTask(id: Long)

    suspend fun deleteAllTasks()

    suspend fun getAllTasks(): List<TaskEntity>
}