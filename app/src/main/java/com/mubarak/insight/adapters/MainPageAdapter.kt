package com.mubarak.insight.adapters


import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mubarak.insight.data.Images
import com.mubarak.insight.databinding.ItemListMainPageBinding
import com.mubarak.insight.fragments.MainPageDirections
import com.mubarak.insight.fragments.ProfileImagesDirections
import kotlinx.android.synthetic.main.item_list_main_page.view.creation_time

//here we declared mainPageAdapter , and take the data from Images data class and save it in a list
class MainPageAdapter : ListAdapter<Images, MainPageAdapter.MainPageViewHolder>(DiffCallback) {

    class MainPageViewHolder(

        var binding: ItemListMainPageBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        //=================================================================================

// here we defined binding fun the we used in bindingAdapter

        fun bind(PhotoItem: Images) {
            binding.apply {

                binding.imageList = PhotoItem
                itemView.creation_time.text =
                    DateUtils.getRelativeTimeSpanString(PhotoItem.creation_time)

            }
        }

        // the pointer here is used the make this item is apply for every item in the recycler
        // for example make every image in the recyclerview clickable
        var pointer = binding.fireImage
    }

    //=================================================================================
// here we check for every item and handle the changes only
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
    ): MainPageViewHolder {
        return MainPageViewHolder(
            ItemListMainPageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }
    //=================================================================================

    override fun onBindViewHolder(holder: MainPageViewHolder, position: Int) {
        val images = getItem(position)
        holder.bind(images)
        //=================================================================================

// here we move data from mainPage fragment to overview fragment
        holder.pointer.setOnClickListener {
            val action = MainPageDirections.actionMainPageToOverview2(
                images.image_url.toString(),
                images.title.toString(),
                images.overview,
                images.link1,
                images.link2,
                images.creation_time
            )
            holder.itemView.findNavController().navigate(action)
        }
//        holder.pointer.setOnClickListener{
//            val action = ProfileImagesDirections.actionProfileImagesToOverview(images.image_url.toString(),images.title.toString(),images.overview, link1 = "google.com", link2 = "yahoo.com",images.creation_time)
//            holder.itemView.findNavController().navigate(action)
//        }
    }
}        //=================================================================================

