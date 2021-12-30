package com.example.insight.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.insight.data.Images
import com.example.insight.data.Item
import com.example.insight.network.FirebaseService
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ListResult
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.lang.Exception

enum class InSightStatus { LOADING, ERROR, DONE }


class ViewModel : ViewModel() {

    private val _photos = MutableLiveData<List<Images>?>()
    val photo: MutableLiveData<List<Images>?> = _photos


    private val _username = MutableLiveData<Images>()
    val username: MutableLiveData<Images> = _username

    init {
        getPhotos()
    }

    fun getPhotos() {
        viewModelScope.launch {
            try {


            } catch (e: Exception) {

            }
        }
    }

//
//    fun imageInfo() {
//        val storage = FirebaseStorage.getInstance()
//        val ref = storage.reference.child("image")
//        val imageList: MutableList<Images> = mutableListOf()
//        val listAllTask: Task<ListResult> = ref.listAll()
//
//        listAllTask.addOnCompleteListener { result ->
//            val items: List<StorageReference> = result.result.items
//            items.forEachIndexed { index, item ->
//                item.downloadUrl.await().path
//            }
//
//        }
//    }
}
