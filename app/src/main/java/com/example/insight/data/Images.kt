package com.example.insight.data

import android.net.Uri
import com.google.firebase.firestore.PropertyName
import retrofit2.http.GET

data class Images(
        @get:PropertyName("image_url") @set:PropertyName("image_url") var image_url:String? = "",
        @get:PropertyName("creation_time") @set:PropertyName("creation_time") var creation_time: Long = 0,
        var name: String? = ""
        )