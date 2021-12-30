package com.example.insight.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.insight.data.Images
import com.example.insight.databinding.ItemListBinding


class MainPageAdapter : ListAdapter<Images, MainPageAdapter.MainPageViewHolder>(DiffCallback) {

    class MainPageViewHolder(

        private var binding: ItemListBinding
    ) : RecyclerView.ViewHolder(binding.root) {


        fun bind(PhotoItem: Images) {
//            list_Item = photo
//
//binding.fireImage.setImageURI("https://firebasestorage.googleapis.com/v0/b/insight-cc302.appspot.com/o/image%2FThu%20Dec%2023%2013%3A16%3A17%20GMT%2B03%3A00%202021.jpg?alt=media&token=70df7a07-ae4d-4712-9595-1c1a976e9ada".toUri())
            binding.imageList = PhotoItem

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
            return oldItem.image_url == newItem.image_url
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


//        holder.pointer.setOnClickListener {
//            holder.itemView.findNavController().navigate(R.id.action_mainPage_to_add2)
//        }
    }
}