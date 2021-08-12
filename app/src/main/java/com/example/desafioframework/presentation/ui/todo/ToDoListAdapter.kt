package com.example.desafioframework.presentation.ui.todo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.desafioframework.R
import com.example.desafioframework.data.model.ToDo
import com.example.desafioframework.databinding.ItemListTodoBinding

class ToDoListAdapter : ListAdapter<ToDo, ToDoListAdapter.ToDoListItemViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoListItemViewHolder {
        val binding = LayoutInflater.from(parent.context).inflate(R.layout.item_list_todo, parent, false)
        return ToDoListItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ToDoListItemViewHolder, position: Int) {
        holder.bindViewHolder(getItem(position))
    }

    inner class ToDoListItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val binding: ItemListTodoBinding = ItemListTodoBinding.bind(itemView)

        fun bindViewHolder(todo: ToDo) {
            binding.tvTodoId.text = "TO-DO #${todo.id}"
            binding.tvTodoTitle.text = todo.title
            binding.tvTodoStatus.text =
                if (todo.completed) itemView.context.getString(R.string.todo_status_completed)
                else itemView.context.getString(R.string.todo_status_incompleted)
        }
    }
}

class DiffCallback : DiffUtil.ItemCallback<ToDo>() {
    override fun areItemsTheSame(oldItem: ToDo, newItem: ToDo) = oldItem == newItem
    override fun areContentsTheSame(oldItem: ToDo, newItem: ToDo) = oldItem.id == newItem.id
}