package com.mubarak.insight.network

import com.mubarak.insight.data.Images


interface FirebaseService{

    suspend fun getPhotos(): Images

}