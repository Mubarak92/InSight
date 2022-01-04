package com.mubarak.insight.binding

import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.mubarak.insight.R
import com.mubarak.insight.adapters.MainPageAdapter
import com.mubarak.insight.data.Images
import com.mubarak.insight.viewmodel.InSightStatus
import com.squareup.picasso.Picasso


@BindingAdapter("listData")              //setData
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Images>?) {
    Log.e("TAG","BindingAdapter: listData $data")
    val adapter = recyclerView.adapter as MainPageAdapter
    adapter.submitList(data)
}

@BindingAdapter("imageFireBase")
fun bindingFirebase(imageView: ImageView,imageUrl:String?){

        Glide.with(imageView).load(imageUrl).into(imageView)
    Picasso.get().load(imageUrl).into(imageView)


    Log.e("hello ","data is here ")
    }


@BindingAdapter("InSightStatus")
fun bindStatus(statusImageView: ImageView,status: InSightStatus?) {
    when (status) {
        InSightStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
        }
        InSightStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_connection_error)
        }
        InSightStatus.DONE -> {
            statusImageView.visibility = View.GONE
        }
    }
}


