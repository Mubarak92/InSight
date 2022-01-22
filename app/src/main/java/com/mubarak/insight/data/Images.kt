package com.mubarak.insight.data

data class Images(
    var image_url: String? = "",
    var title: String? = "",
    var creation_time: Long = 0,
    var overview: String = "",
    var username: String = "",
    var link1: String = "",
    var link2: String = ""
)