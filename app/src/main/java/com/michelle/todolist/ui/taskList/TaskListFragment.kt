package com.michelle.todolist.ui.taskList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.michelle.todolist.data.db.AppDataBase
import com.michelle.todolist.data.db.dao.TaskDAO
import com.michelle.todolist.data.db.entity.TaskEntity
import com.michelle.todolist.data.repository.DataBaseDataSource
import com.michelle.todolist.data.repository.TaskRepository
import com.michelle.todolist.databinding.TaskListFragmentBinding

class TaskListFragment : Fragment() {
    private lateinit var binding: TaskListFragmentBinding
    private val viewModel: TaskListViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                val taskDAO: TaskDAO = AppDataBase.getInstance(requireContext()).taskDao
                val repository: TaskRepository = DataBaseDataSource(taskDAO)
                return TaskListViewModel(repository) as T
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = TaskListFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeEvents()
    }

    private fun observeEvents() {
        viewModel.taskList.observe(viewLifecycleOwner) { taskList ->
            binding.RVTaskList.adapter = TaskListAdapter(list = taskList)
        }
    }
}