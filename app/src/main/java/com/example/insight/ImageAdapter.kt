package com.example.insight

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.insight.data.Item
import com.squareup.picasso.Picasso
import java.lang.System.load

class ImageAdapter(private var items:List<Item>,private val context: Context):
RecyclerView.Adapter<ImageAdapter.ViewHolder>(){
                   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_list, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ImageAdapter.ViewHolder, position: Int) {
        val item = items[position]
Glide.with(context).load(item.imageUrl).into(holder.imageView)

    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.image_view)
    }

}