package com.michelle.todolist.ui.task

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.michelle.todolist.R
import com.michelle.todolist.data.db.AppDataBase
import com.michelle.todolist.data.db.dao.TaskDAO
import com.michelle.todolist.data.repository.DataBaseDataSource
import com.michelle.todolist.data.repository.TaskRepository
import com.michelle.todolist.databinding.TaskFragmentBinding
import com.michelle.todolist.util.hideKeyboard

class TaskFragment : Fragment() {
    private lateinit var binding: TaskFragmentBinding
    private val viewModel: TaskViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                val taskDAO: TaskDAO = AppDataBase.getInstance(requireContext()).taskDao
                val repository: TaskRepository = DataBaseDataSource(taskDAO)
                return TaskViewModel(repository) as T
            }
        }
    }

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

        observeEvents()
        setListeners()
    }

    private fun observeEvents() {
        viewModel.taskInsertedData.observe(viewLifecycleOwner) { isInserted ->
            if (isInserted) {
                clearFields()
                hideKeyboard()
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
                viewModel.addTask(title, description)

        }
    }
}