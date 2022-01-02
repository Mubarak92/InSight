package com.mubarak.insight.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mubarak.insight.R
import com.mubarak.insight.data.Images
import com.mubarak.insight.databinding.ItemListBinding


class MainPageAdapter : ListAdapter<Images, MainPageAdapter.MainPageViewHolder>(DiffCallback) {

    class MainPageViewHolder(

        private var binding: ItemListBinding
    ) : RecyclerView.ViewHolder(binding.root) {


        fun bind(PhotoItem: Images) {

            binding.tvUsername.text.toString()
            Glide.with(binding.fireImage).load(PhotoItem.image_url).into(binding.fireImage)
            // This is important, because it forces the data binding to execute immediately,
            // which allows the RecyclerView to make the correct view size measurements
            binding.executePendingBindings()
        }

        var pointer = binding.fireImage
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Images>() {
        override fun areItemsTheSame(oldItem: Images, newItem: Images): Boolean {
            return oldItem.image_url == newItem.image_url
        }

        override fun areContentsTheSame(oldItem: Images, newItem: Images): Boolean {
            return oldItem.creation_time == newItem.creation_time
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MainPageViewHolder {
        return MainPageViewHolder(
            ItemListBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun onBindViewHolder(holder: MainPageViewHolder, position: Int) {
        val photo = getItem(position)
        holder.bind(photo)

//
//        holder.pointer.setOnClickListener {
//
//        }
    }
}