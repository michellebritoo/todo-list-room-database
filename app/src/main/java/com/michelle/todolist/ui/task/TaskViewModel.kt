package com.michelle.todolist.ui.task

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.michelle.todolist.R
import com.michelle.todolist.data.repository.TaskRepository
import com.michelle.todolist.ui.task.TaskViewModel.TaskState.Inserted
import com.michelle.todolist.ui.task.TaskViewModel.TaskState.Updated
import kotlinx.coroutines.launch

class TaskViewModel(
    private val repository: TaskRepository
) : ViewModel() {
    val taskInsertedData: LiveData<TaskState> get() = _taskInsertedData
    val taskMessageData: LiveData<Int> get() = _taskMessageData
    private val _taskInsertedData = MutableLiveData<TaskState>()
    private val _taskMessageData = MutableLiveData<Int>()

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

            _taskInsertedData.value = Updated
            _taskMessageData.value = R.string.task_updated_successfully
        } catch (ex: Exception) {
            _taskMessageData.value = R.string.task_error_to_update
        }
    }

    private fun insertTask(title: String, description: String) = viewModelScope.launch {
        try {
            repository.insertTask(title, description).let { id ->
                if (id > 0) {
                    _taskInsertedData.value = Inserted
                    _taskMessageData.value = R.string.task_insert_successfully
                }
            }

        } catch (exception: Exception) {
            _taskMessageData.value = R.string.task_error_to_insert
            Log.i(TAG, exception.message.toString())
        }
    }

    sealed class TaskState {
        object Inserted : TaskState()
        object Updated : TaskState()
    }

    companion object {
        private val TAG = TaskViewModel::class.java.name
    }
}