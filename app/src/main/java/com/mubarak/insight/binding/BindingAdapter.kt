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


@BindingAdapter("imageUrl")
fun ImageView.bindImage(imageUrl: String?){
    var image= imageUrl?.toUri()?.buildUpon()?.build()
    Glide.with(this)
        .load(image)
        .into(this)
}


@BindingAdapter("listData")
fun RecyclerView.bindRecyclerView(data: List<Images>?) {
    val adapter = this.adapter as MainPageAdapter
    setOf(adapter)
}



@BindingAdapter("imageFireBase")
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


