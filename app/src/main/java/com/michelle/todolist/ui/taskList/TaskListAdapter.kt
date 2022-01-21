package com.michelle.todolist.ui.taskList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.michelle.todolist.R
import com.michelle.todolist.data.db.entity.TaskEntity
import com.michelle.todolist.databinding.TaskItemBinding
import com.michelle.todolist.ui.taskList.TaskListAdapter.TaskListViewHolder

class TaskListAdapter(
    private val list: List<TaskEntity>
) : Adapter<TaskListViewHolder>() {

    var onItemClick: ((task: TaskEntity) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.task_item, parent, false)

        return TaskListViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskListViewHolder, position: Int) {
        holder.bindView(list[position])
    }

    override fun getItemCount() = list.size


    inner class TaskListViewHolder(itemView: View) : ViewHolder(itemView) {
        private val taskItem = TaskItemBinding.bind(itemView)

        fun bindView(task: TaskEntity) {
            with(taskItem) {
                TVTaskTitle.text = task.title
                TVTaskDescription.text = task.description
            }

            itemView.setOnClickListener {
                onItemClick?.invoke(task)
            }
        }
    }
}