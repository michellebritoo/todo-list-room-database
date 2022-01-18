package com.michelle.todolist.ui.task

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.michelle.todolist.R
import com.michelle.todolist.data.repository.TaskRepository
import kotlinx.coroutines.launch

class TaskViewModel(
    private val repository: TaskRepository
) : ViewModel() {
    val taskInsertedData: LiveData<Boolean> get() = _taskInsertedData
    val taskMessageData: LiveData<Int> get() = _taskMessageData
    private val _taskInsertedData = MutableLiveData<Boolean>()
    private val _taskMessageData = MutableLiveData<Int>()

    init {
        _taskInsertedData.value = false
    }

    fun addTask(title: String, description: String) = viewModelScope.launch {
        try {
            repository.insertTask(title, description).let { id ->
                if (id > 0) {
                    _taskInsertedData.value = true
                    _taskMessageData.value = R.string.task_insert_successfully
                }
            }

        } catch (exception: Exception) {
            _taskMessageData.value = R.string.task_error_to_insert
            Log.i(TAG, exception.message.toString())
        }
    }

    companion object {
        private val TAG = TaskViewModel::class.java.name
    }
}