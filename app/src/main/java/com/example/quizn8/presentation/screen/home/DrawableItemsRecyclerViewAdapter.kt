package com.example.quizn8.presentation.screen.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.quizn8.databinding.RecyclerDrawableItemBinding
import com.example.quizn8.presentation.model.DrawerItem

class DrawableItemsRecyclerViewAdapter: ListAdapter<DrawerItem, DrawableItemsRecyclerViewAdapter.DrawableItemViewHolder>(
    CategoriesItemDiffCallback
) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrawableItemViewHolder {
        return DrawableItemViewHolder(RecyclerDrawableItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: DrawableItemViewHolder, position: Int) {
        holder.bind()
    }

    inner class DrawableItemViewHolder(private val binding: RecyclerDrawableItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind() {
            val item = currentList[adapterPosition]
            with(binding) {
                ivItemIcon.setImageResource(item.image)
                tvItemText.text = item.text
                item.notifications?.let {
                    tvNotificationNumber.text = it.toString()
                    tvNotificationNumber.visibility = View.VISIBLE
                }
            }

        }
    }

    companion object {
        private val CategoriesItemDiffCallback = object : DiffUtil.ItemCallback<DrawerItem>() {

            override fun areItemsTheSame(oldItem: DrawerItem, newItem: DrawerItem): Boolean {
                return oldItem.text == newItem.text
            }

            override fun areContentsTheSame(oldItem: DrawerItem, newItem: DrawerItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}