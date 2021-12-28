package com.example.insight.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.insight.R
import com.example.insight.data.Images


class MainPageAdapter(private var Images: MutableList<Images>, private val context: Context):
RecyclerView.Adapter<MainPageAdapter.ViewHolder>(){
                   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_list, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val Images = Images[position]
Glide.with(context).load(Images.image_url).into(holder.imageView)
//        Glide.with(context).load(Images.image_url).into(holder.profile)

    }

    override fun getItemCount(): Int {
        return Images.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.image_view)
//        val profile:ImageView = view.findViewById(R.id.profile_image)
    }

}