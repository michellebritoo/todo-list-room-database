package com.michelle.todolist.ui.task

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import com.michelle.todolist.R
import com.michelle.todolist.databinding.TaskFragmentBinding

class TaskFragment : Fragment() {
    private val viewModel: TaskViewModel by viewModels()
    private lateinit var binding: TaskFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = TaskFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}