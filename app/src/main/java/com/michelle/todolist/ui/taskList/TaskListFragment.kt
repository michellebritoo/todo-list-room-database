package com.michelle.todolist.ui.taskList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.michelle.todolist.R
import com.michelle.todolist.databinding.TaskListFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class TaskListFragment : Fragment() {
    private lateinit var binding: TaskListFragmentBinding
    private val viewModel: TaskListViewModel by viewModel()

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
        setListeners()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getTasks()
    }

    private fun observeEvents() {
        viewModel.taskList.observe(viewLifecycleOwner) { taskList ->
            binding.RVTaskList.adapter = TaskListAdapter(list = taskList).apply {
                onItemClick = { task ->
                    val directions =
                        TaskListFragmentDirections.actionTaskListFragmentToTaskFragment(task)
                    findNavController().navigate(directions)
                }
            }
        }
    }

    private fun setListeners() {
        binding.btnAdd.setOnClickListener {
            findNavController().navigate(R.id.action_taskListFragment_to_taskFragment)
        }
    }
}