package com.example.insight

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.insight.data.Images
import com.squareup.picasso.Picasso

class MainPageAdapter(private var items:MutableList<Images>, private val context: Context):
RecyclerView.Adapter<MainPageAdapter.ViewHolder>(){
                   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_list, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: MainPageAdapter.ViewHolder, position: Int) {
        val item = items[position]
Picasso.get().load(item.image_url).into(holder.imageView)


    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.fire_image)
    }

}