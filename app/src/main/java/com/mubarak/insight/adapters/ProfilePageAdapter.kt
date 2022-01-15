package com.mubarak.insight.adapters
//
//import android.text.format.DateUtils
//import android.view.LayoutInflater
//import android.view.ViewGroup
//import androidx.recyclerview.widget.DiffUtil
//import androidx.recyclerview.widget.ListAdapter
//import androidx.recyclerview.widget.RecyclerView
//import com.mubarak.insight.data.Images
//import com.mubarak.insight.databinding.ItemListProfilePageBinding
//import kotlinx.android.synthetic.main.item_list_main_page.view.*
//
//
//class ProfilePageAdapter : ListAdapter<Images, ProfilePageAdapter.ProfilePageViewHolder>(DiffCallback) {
//
//    class ProfilePageViewHolder(
//
//        var binding: ItemListProfilePageBinding
//    ) : RecyclerView.ViewHolder(binding.root) {
//
//
//        fun bind(PhotoItemProfile: Images) {
//            binding.apply {
//
//                binding.imageList = PhotoItemProfile
//                itemView.creation_time.text =
//                    DateUtils.getRelativeTimeSpanString(PhotoItemProfile.creation_time)
//
//            }
//        }
//
//        var pointer = binding.fireImage
//    }
//
//
//    companion object DiffCallback : DiffUtil.ItemCallback<Images>() {
//        override fun areItemsTheSame(oldItem: Images, newItem: Images): Boolean {
//            return oldItem.image_url == newItem.image_url
//        }
//
//        override fun areContentsTheSame(oldItem: Images, newItem: Images): Boolean {
//            return oldItem.image_url == newItem.image_url
//        }
//    }
//
//    override fun onCreateViewHolder(
//        parent: ViewGroup,
//        viewType: Int
//    ): ProfilePageViewHolder {
//        return ProfilePageViewHolder(
//            ItemListProfilePageBinding.inflate(LayoutInflater.from(parent.context),parent,false)
//        )
//    }
//
//    override fun onBindViewHolder(holder: ProfilePageViewHolder, position: Int) {
//        val images = getItem(position)
//        holder.bind(images)
//
////
////        holder.pointer.setOnClickListener{
////            val action = MainPageDirections.mainToOverview(position)
////            holder.itemView.findNavController().navigate(action)
////        }
//
//
//    }
//
//
//}
