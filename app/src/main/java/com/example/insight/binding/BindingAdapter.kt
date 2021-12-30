package com.example.insight.binding

import android.util.Log
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.insight.adapters.MainPageAdapter
import com.example.insight.data.Images


@BindingAdapter("listData")              //setData
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Images>?) {
    Log.e("TAG","BindingAdapter: listData $data")
    val adapter = recyclerView.adapter as MainPageAdapter
    adapter.submitList(data)
}

@BindingAdapter("imageFireBase")
fun bindingFirebase(imageView: ImageView,imageUrl:String?){
    imageUrl?.let {
        Log.e("xbhjdghjxhk","xbjswdjhs")
      imageUrl.toUri().buildUpon().build()
        Glide.with(imageView).load(imageUrl).into(imageView)
        Log.e("xbhjdghjxhk","xbjswdjhs")
    }
}