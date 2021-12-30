package com.example.insight.network

import com.example.insight.data.Images


interface FirebaseService{

    suspend fun getPhotos(): Images

}