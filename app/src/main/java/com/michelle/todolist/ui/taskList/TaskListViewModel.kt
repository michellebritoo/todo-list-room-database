package com.michelle.todolist.ui.taskList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.michelle.todolist.data.db.entity.TaskEntity
import com.michelle.todolist.data.repository.TaskRepository
import kotlinx.coroutines.launch

class TaskListViewModel(
    private val repository: TaskRepository
) : ViewModel() {
    val taskList : LiveData<List<TaskEntity>> get() = _taskList
    private val _taskList = MutableLiveData<List<TaskEntity>>()

    fun getTasks() = viewModelScope.launch {
        _taskList.value = repository.getAllTasks()
    }
}