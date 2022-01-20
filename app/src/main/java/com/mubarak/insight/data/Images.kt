package com.mubarak.insight.data

import com.firebase.ui.auth.data.model.User
import com.google.firebase.firestore.PropertyName


data class Images(
    var image_url: String? = "",
    var title: String? = "",
    var creation_time: Long = 0,
    var overview: String = "",
    var username: String = "",
)