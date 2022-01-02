package com.mubarak.insight.data

import com.firebase.ui.auth.data.model.User
import com.google.firebase.firestore.PropertyName


data class Images(
    @get:PropertyName("image_url") @set:PropertyName("image_url") var image_url: String? = "",
    @get:PropertyName("title") @set:PropertyName("title") var title: String? = "",
    @get:PropertyName("creation_time") @set:PropertyName("creation_time") var creation_time: Long = 0,
    var user: User? = null,
)