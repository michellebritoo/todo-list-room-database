package com.michelle.todolist.ui.taskList

import androidx.lifecycle.ViewModel
import com.michelle.todolist.data.repository.TaskRepository

class TaskListViewModel(
    private val repository: TaskRepository
) : ViewModel() {
    val taskList = repository.getAllTasks()
}