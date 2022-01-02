package com.mubarak.insight.viewmodel

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.lifecycle.*
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.mubarak.insight.data.Images
import com.mubarak.insight.databinding.FragmentAddBinding
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.coroutines.launch
import java.lang.Exception
import java.util.HashMap

private const val PICK_PHOTO_CODE = 1234

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
class SaveFirebase {
    fun save(uri: String, systemTime: Long = System.currentTimeMillis(), title: String, ) {
        val db = FirebaseFirestore.getInstance()
        Firebase.auth
        Log.e("TAG", "save: start")


        val data: MutableMap<String, Any> = HashMap()
        data["image_url"] = uri
        data["creation_time"] = systemTime
        data["title"] = title


        db.collection("images")
            .add(data)
            .addOnSuccessListener {
                // Toast.makeText(this, "Working", Toast.LENGTH_SHORT).show()
                Log.e("TAG", "save: true")
            }
            .addOnFailureListener { e ->
                Log.e("TAG", "save: error $e")

                //                Toast.makeText(this, "Fail $e", Toast.LENGTH_SHORT).show()
            }
    }

}

