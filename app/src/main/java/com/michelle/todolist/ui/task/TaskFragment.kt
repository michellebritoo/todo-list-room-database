package com.michelle.todolist.ui.task

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.michelle.todolist.R
import com.michelle.todolist.databinding.TaskFragmentBinding
import com.michelle.todolist.ui.task.TaskViewModel.TaskState.Deleted
import com.michelle.todolist.ui.task.TaskViewModel.TaskState.Inserted
import com.michelle.todolist.ui.task.TaskViewModel.TaskState.Updated
import com.michelle.todolist.util.hideKeyboard
import org.koin.androidx.viewmodel.ext.android.viewModel

class TaskFragment : Fragment() {
    private lateinit var binding: TaskFragmentBinding
    private val args: TaskFragmentArgs? by navArgs()
    private val viewModel: TaskViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = TaskFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        args?.taskData?.let { task ->
            binding.TIETTaskTitle.setText(task.title)
            binding.TIETTaskDescription.setText(task.description.toString())
            binding.btnRegister.text = getString(R.string.btn_update)
            binding.btnDelete.visibility = View.VISIBLE
        }

        observeEvents()
        setListeners()
    }

    private fun observeEvents() {
        viewModel.taskState.observe(viewLifecycleOwner) { taskState ->
            when (taskState) {
                is Inserted,
                is Updated,
                is Deleted -> {
                    clearFields()
                    hideKeyboard()
                }
            }
        }
        viewModel.taskMessageData.observe(viewLifecycleOwner) { stringResID ->
            Snackbar.make(requireView(), stringResID, Snackbar.LENGTH_LONG).show()

            findNavController().popBackStack()
        }
    }

    private fun clearFields() {
        binding.TIETTaskTitle.text?.clear()
        binding.TIETTaskDescription.text?.clear()
        binding.TILTaskTitle.error = null
    }

    private fun hideKeyboard() {
        val parentActivity = requireActivity()
        if (parentActivity is AppCompatActivity) {
            parentActivity.hideKeyboard()
        }
    }

    private fun setListeners() {
        binding.btnRegister.setOnClickListener {
            val title = binding.TIETTaskTitle.text.toString()
            val description = binding.TIETTaskDescription.text.toString()

            if (title.isEmpty())
                binding.TILTaskTitle.error = getString(R.string.error_task_title)
            else
                viewModel.addOrUpdateTask(title, description, args?.taskData?.id ?: 0)
        }

        binding.btnDelete.setOnClickListener {
            viewModel.removeTask(args?.taskData?.id ?: 0)
        }
    }
}