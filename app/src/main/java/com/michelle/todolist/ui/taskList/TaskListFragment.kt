package com.michelle.todolist.ui.taskList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.michelle.todolist.R
import com.michelle.todolist.data.db.entity.TaskEntity
import com.michelle.todolist.databinding.TaskFragmentBinding
import com.michelle.todolist.databinding.TaskListFragmentBinding

class TaskListFragment : Fragment() {
    private lateinit var viewModel: TaskListViewModel
    private lateinit var binding: TaskListFragmentBinding

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

        val taskListAdapter = TaskListAdapter(
            list = listOf(
                TaskEntity(id = 1, title = "teste", description = "desc"),
                TaskEntity(id = 1, title = "teste", description = "desc")
            )
        )

        binding.RVTaskList.adapter = taskListAdapter
    }
}