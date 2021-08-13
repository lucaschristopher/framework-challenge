package com.example.desafioframework.presentation.ui.album

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.desafioframework.R
import com.example.desafioframework.data.model.Album
import com.example.desafioframework.databinding.ItemListAlbumBinding

class AlbumListAdapter : ListAdapter<Album, AlbumListAdapter.AlbumListItemViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumListItemViewHolder {
        val binding = LayoutInflater.from(parent.context).inflate(R.layout.item_list_album, parent, false)
        return AlbumListItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AlbumListItemViewHolder, position: Int) {
        holder.bindViewHolder(getItem(position))
    }

    inner class AlbumListItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val binding: ItemListAlbumBinding = ItemListAlbumBinding.bind(itemView)

        fun bindViewHolder(album: Album) {
            binding.tvAlbumId.text = "ALBUM #${album.id}"
            binding.tvAlbumTitle.text = album.title
        }
    }
}

class DiffCallback : DiffUtil.ItemCallback<Album>() {
    override fun areItemsTheSame(oldItem: Album, newItem: Album) = oldItem == newItem
    override fun areContentsTheSame(oldItem: Album, newItem: Album) = oldItem.id == newItem.id
}