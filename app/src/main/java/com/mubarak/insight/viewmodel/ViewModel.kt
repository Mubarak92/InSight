package com.mubarak.insight.viewmodel

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.mubarak.insight.data.Images
import com.mubarak.insight.data.Users
import com.mubarak.insight.fragments.LoginPage
import com.mubarak.insight.fragments.Overview
import com.mubarak.insight.fragments.Profile
import com.mubarak.insight.fragments.RegisterPage
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.coroutines.launch
import java.lang.Exception
import java.util.HashMap
import kotlin.reflect.KProperty

private const val PICK_PHOTO_CODE = 1234

enum class InSightStatus { LOADING, ERROR, DONE }


class ViewModel : ViewModel() {


    private val _list = MutableLiveData<List<Users>>()
    val list: MutableLiveData<List<Users>> get() = _list

    private val _imagelist = MutableLiveData<List<Images>>()
    val imagelist: MutableLiveData<List<Images>> get() = _imagelist

    private val _photos = MutableLiveData<String>()
    val photos: MutableLiveData<String> get() = _photos

    private var _username = MutableLiveData<String>()
    val username: MutableLiveData<String> get() = _username

    private val _creationTime = MutableLiveData<Int>()
    val creationTime: MutableLiveData<Int> = _creationTime

    private var _title = MutableLiveData<String>()
    val title: MutableLiveData<String> get() = _title

    private var _overview = MutableLiveData<String>()
    val overview: MutableLiveData<String> get() = _overview

    private var _link = MutableLiveData<String>()
    val link: MutableLiveData<String> get() = _link


//    private var _username = MutableLiveData<String>()
//    val username: MutableLiveData<String> get() =  _username


    private val _status = MutableLiveData<InSightStatus>()

    val status: LiveData<InSightStatus> = _status
    private val imageLink = MutableLiveData<String>()


    private val mFirestore = FirebaseFirestore.getInstance()

    fun getUserId(): String {
        val currentUser = FirebaseAuth.getInstance().currentUser!!

        // A variable to assign the currentUserId if it is not null or else it will be blank.
        var currentUserID = ""
        if (currentUser != null) {
            currentUserID = currentUser.uid
        }

        return currentUserID
    }



    operator fun setValue(overview: Overview, property: KProperty<*>, viewModel: ViewModel) {

    }
}

class SaveFirebase {
    fun save(uri: String, systemTime: Long = System.currentTimeMillis(), title: String) {
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

