package com.michelle.todolist.ui.task

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.michelle.todolist.R
import com.michelle.todolist.data.repository.TaskRepository
import com.michelle.todolist.ui.task.TaskViewModel.TaskState.Deleted
import com.michelle.todolist.ui.task.TaskViewModel.TaskState.Inserted
import com.michelle.todolist.ui.task.TaskViewModel.TaskState.Updated
import kotlinx.coroutines.launch

class TaskViewModel(
    private val repository: TaskRepository
) : ViewModel() {
    val taskState: LiveData<TaskState> get() = _taskStateData
    val taskMessageData: LiveData<Int> get() = _taskMessageData
    private val _taskStateData = MutableLiveData<TaskState>()
    private val _taskMessageData = MutableLiveData<Int>()

    fun removeTask(id: Long) = viewModelScope.launch {
        try {
            if (id > 0) {
                repository.deleteTask(id)
                _taskStateData.value = Deleted
                _taskMessageData.value = R.string.task_delete_successfully
            }

        } catch (ex: Exception) {
            _taskMessageData.value = R.string.task_delete_error
            Log.i(TAG, ex.toString())
        }
    }

    fun addOrUpdateTask(title: String, description: String, id: Long = 0) {
        if (id > 0) {
            updateTask(id, title, description)
        } else {
            insertTask(title, description)
        }
    }

    private fun updateTask(id: Long, title: String, description: String) = viewModelScope.launch {
        try {
            repository.updateTask(id, title, description)

            _taskStateData.value = Updated
            _taskMessageData.value = R.string.task_updated_successfully
        } catch (ex: Exception) {
            _taskMessageData.value = R.string.task_error_to_update
        }
    }

    private fun insertTask(title: String, description: String) = viewModelScope.launch {
        try {
            repository.insertTask(title, description).let { id ->
                if (id > 0) {
                    _taskStateData.value = Inserted
                    _taskMessageData.value = R.string.task_insert_successfully
                }
            }

        } catch (ex: Exception) {
            _taskMessageData.value = R.string.task_error_to_insert
            Log.i(TAG, ex.message.toString())
        }
    }

    sealed class TaskState {
        object Inserted : TaskState()
        object Updated : TaskState()
        object Deleted : TaskState()
    }

    companion object {
        private val TAG = TaskViewModel::class.java.name
    }
}