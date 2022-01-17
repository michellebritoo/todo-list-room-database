package com.michelle.todolist.data.repository

import com.michelle.todolist.data.db.dao.TaskDAO
import com.michelle.todolist.data.db.entity.TaskEntity

class DataBaseDataSource(
    private val taskDAO: TaskDAO
) : TaskRepository {
    override suspend fun insertTask(title: String, description: String): Long {
        return taskDAO.insertTask(
            TaskEntity(
                title = title,
                description = description
            )
        )
    }

    override suspend fun updateTask(id: Long, title: String, description: String) {
        taskDAO.updateTask(
            TaskEntity(
                id = id,
                title = title,
                description = description
            )
        )
    }

    override suspend fun deleteTask(id: Long) = taskDAO.deleteTask(id)

    override suspend fun deleteAllTasks() = taskDAO.deleteAllTasks()

    override suspend fun getAllTasks() = taskDAO.getAllTasks()

}