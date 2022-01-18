package com.michelle.todolist.ui.task

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.michelle.todolist.R
import com.michelle.todolist.data.db.AppDataBase
import com.michelle.todolist.data.db.dao.TaskDAO
import com.michelle.todolist.data.repository.DataBaseDataSource
import com.michelle.todolist.data.repository.TaskRepository

class TaskFragment : Fragment(R.layout.task_fragment) {
    private val viewModel: TaskViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                val taskDAO: TaskDAO = AppDataBase.getInstance(requireContext()).taskDao
                val repository: TaskRepository = DataBaseDataSource(taskDAO)
                return TaskViewModel(repository) as T
            }
        }
    }
}