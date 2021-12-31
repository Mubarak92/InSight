package com.example.insight.viewmodel

import android.util.Log
import androidx.lifecycle.*
import androidx.lifecycle.ViewModel
import com.example.insight.data.Images
import kotlinx.coroutines.launch
import java.lang.Exception

enum class InSightStatus { LOADING, ERROR, DONE }


class ViewModel : ViewModel() {

    private val _photos = MutableLiveData<List<Images>?>()
    val photo: MutableLiveData<List<Images>?> = _photos

    private val _status = MutableLiveData<InSightStatus>()

    // The external immutable LiveData for the request status
    val status: LiveData<InSightStatus> = _status


    val image_url = MutableLiveData<String>()


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


    fun imageInfo(index: Int) {
        val item = _photos.value?.get(index)
        Log.e("image","Before")
        image_url.value = item?.image_url
        Log.e("image"," ${image_url.value}")

        Log.e("image","After")

    }
}


//
//    fun imageInfo() {
//
//
//        listAllTask.addOnCompleteListener { result ->
//            val items: List<StorageReference> = result.result.items
//
//            val li: MutableList<Images> = mutableListOf()
//
//
//            lifecycleScope.async {
//
//
//                val l = async {
//                    items.forEachIndexed { index, item ->
//                        li.add(Images(item.downloadUrl.await().toString()))
//                    }
//                    viewModel.photo.value = li
//
//                    return@async li
//                    // Sets the adapter of the photosGrid RecyclerView
//
//                }
//                Log.d("TAG", "onViewCreated: ${l.await().toString()} ")
//            }
//
//
//        }
//    }
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

