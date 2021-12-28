package com.example.insight.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.insight.data.Images
import com.example.insight.data.Item

enum class MovieApiStatus { LOADING, ERROR, DONE }


class ViewModel : ViewModel() {

    private val _photos = MutableLiveData<List<Item>>()
    val photo: MutableLiveData<List<Item>> =  _photos


    private val _username = MutableLiveData<Images>()
    val username:MutableLiveData<Images> = _username



    fun imageInfo(){

    }


}
