package com.mubarak.insight.adapters

import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mubarak.insight.data.Images
import com.mubarak.insight.databinding.ItemListProfilePageBinding
import com.mubarak.insight.fragments.ProfileImagesDirections
import kotlinx.android.synthetic.main.item_list_main_page.view.*


class ProfilePageAdapter : ListAdapter<Images, ProfilePageAdapter.ProfilePageViewHolder>(DiffCallback) {

    class ProfilePageViewHolder(
        //=================================================================================

        var binding: ItemListProfilePageBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        //=================================================================================


        fun bind(PhotoItem: Images) {
            binding.apply {

                binding.imageList = PhotoItem
//                itemView.creation_time.text =
//                    DateUtils.getRelativeTimeSpanString(PhotoItem.creation_time)

            }
        }

        var pointer = binding.fireImage
    }
    //=================================================================================


    companion object DiffCallback : DiffUtil.ItemCallback<Images>() {
        override fun areItemsTheSame(oldItem: Images, newItem: Images): Boolean {
            return oldItem.image_url == newItem.image_url
        }

        override fun areContentsTheSame(oldItem: Images, newItem: Images): Boolean {
            return oldItem.image_url == newItem.image_url
        }
    }
    //=================================================================================

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProfilePageViewHolder {
        return ProfilePageViewHolder(
            ItemListProfilePageBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }        //=================================================================================


    override fun onBindViewHolder(holder: ProfilePageViewHolder, position: Int) {
        val images = getItem(position)
        holder.bind(images)

//        //=================================================================================
//        holder.pointer.setOnClickListener{
//            val action = ProfileImagesDirections.actionProfileImagesToOverview(images.image_url.toString(),images.title.toString(),images.overview, link1 = "google.com", link2 = "yahoo.com",images.creation_time)
//            holder.itemView.findNavController().navigate(action)
//        }


    }

    //=================================================================================

}
