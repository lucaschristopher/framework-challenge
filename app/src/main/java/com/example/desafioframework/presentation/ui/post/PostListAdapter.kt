package com.example.desafioframework.presentation.ui.post

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.desafioframework.R
import com.example.desafioframework.data.model.Post
import com.example.desafioframework.databinding.ItemListPostBinding

class PostListAdapter : ListAdapter<Post, PostListAdapter.PostListItemViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostListItemViewHolder {
        val binding = LayoutInflater.from(parent.context).inflate(R.layout.item_list_post, parent, false)
        return PostListItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostListItemViewHolder, position: Int) {
        holder.bindViewHolder(getItem(position))
    }

    inner class PostListItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val binding: ItemListPostBinding = ItemListPostBinding.bind(itemView)

        fun bindViewHolder(post: Post) {
            binding.tvPostId.text = "POST #${post.id}"
            binding.tvPostTitle.text = post.title
            binding.tvPostBody.text = post.body
        }
    }
}

class DiffCallback : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post) = oldItem == newItem
    override fun areContentsTheSame(oldItem: Post, newItem: Post) = oldItem.id == newItem.id
}