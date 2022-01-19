package com.michelle.todolist.ui.taskList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.michelle.todolist.R
import com.michelle.todolist.data.db.entity.TaskEntity
import com.michelle.todolist.databinding.TaskItemBinding

class TaskListAdapter(
    private val list: List<TaskEntity>
) : RecyclerView.Adapter<TaskListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.task_item, parent, false)

        return TaskListViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskListViewHolder, position: Int) {
        holder.bindView(list[position])
    }


    override fun getItemCount() = list.size
}

class TaskListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val taskItem = TaskItemBinding.bind(itemView)

    fun bindView(task: TaskEntity) {
        taskItem.apply {
            this.TVTaskTitle.text = task.title
            this.TVTaskDescription.text = task.title
        }
    }
}