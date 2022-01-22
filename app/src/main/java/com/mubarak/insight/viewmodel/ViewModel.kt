package com.mubarak.insight.viewmodel

import android.util.Log
import androidx.lifecycle.*
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mubarak.insight.data.Images
import com.mubarak.insight.data.Users
import com.mubarak.insight.fragments.Overview
import java.util.HashMap
import kotlin.reflect.KProperty

private const val PICK_PHOTO_CODE = 1234

enum class InSightStatus { LOADING, ERROR, DONE }


class ViewModel : ViewModel() {

    //===========================================================================================================
    private var _users = MutableLiveData<List<Users>>()
    val users: MutableLiveData<List<Users>> get() = _users

    private var _id = MutableLiveData<String>()
    val id: MutableLiveData<String> get() = _id

    private var _username = MutableLiveData<String>()
    val username: MutableLiveData<String> get() = _username

    private var _email = MutableLiveData<String>()
    val email: MutableLiveData<String> get() = _email

    private var _user1 = MutableLiveData<String>()
    val user1: MutableLiveData<String> get() = _user1


//===========================================================================================================

    private var _images = MutableLiveData<List<Images>>()
    val images: MutableLiveData<List<Images>> get() = _images

    private var _imageUrl = MutableLiveData<String>()
    val imageUrl: MutableLiveData<String> get() = _imageUrl

    private var _title = MutableLiveData<String>()
    val title: MutableLiveData<String> get() = _title

    private var _creationTime = MutableLiveData<String>()
    val creationTime: MutableLiveData<String> get() = _creationTime


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

    fun getName(){
        val id = Firebase.auth.currentUser!!.uid

        Firebase.firestore.collection("Users").whereEqualTo("id",id)
            .get()
            .addOnCompleteListener(OnCompleteListener<QuerySnapshot?> { task ->
                if (task.isSuccessful) {
                    for (documentSnapshot in task.result.documents) {

                        _username.value = documentSnapshot.data?.get("username").toString()

                    }
                }
            })
    }




    operator fun setValue(overview: Overview, property: KProperty<*>, viewModel: ViewModel) {

    }

    init {
        getImages()
        getName().toString()
    }


    fun imageInfo(creation_time: String) {
        val item = _images.value?.get(creation_time.toInt())
        imageUrl.value = item?.image_url
        title.value = item?.title

    }

    private fun getImages() {
        users.value = listOf()
        username.value
    }
}


    fun saveImages(
        uri: String,
        systemTime: Long = System.currentTimeMillis(),
        title: String,
        overview: String,
        username: String,
        uid: String,
        link1:String,
        link2:String
    ) {
        val db = FirebaseFirestore.getInstance()
        Firebase.auth
        Log.e("TAG", "save: start")


        val data: MutableMap<String, Any> = HashMap()
        data["image_url"] = uri
        data["creation_time"] = systemTime
        data["title"] = title
        data["overview"] = overview
        data["username"] = username
        data["uid"] = uid
        data["link1"] = link1
        data["link2"] = link2



        db.collection("Users").document(FirebaseAuth.getInstance().currentUser!!.uid).collection("Images")
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



class SaveFirebase {
    fun save(
        uri: String,
        systemTime: Long = System.currentTimeMillis(),
        title: String,
        overview: String,
        username: String,
        uid:String,
        link1:String,
        link2:String
    ) {
        val db = FirebaseFirestore.getInstance()
        Firebase.auth
        Log.e("TAG", "save: start")


        val data: MutableMap<String, Any> = HashMap()
        data["image_url"] = uri
        data["creation_time"] = systemTime
        data["title"] = title
        data["overview"] = overview
        data["username"] = username
        data["uid"] = uid
        data["link1"] = link1
        data["link2"] = link2



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

class EditFirebase {
    fun edit(uri: String, username: String) {
        val db = FirebaseFirestore.getInstance()
        Firebase.auth
        Log.e("TAG", "save: start")


        val data: MutableMap<String, Any> = HashMap()
        data["id"] = FirebaseAuth.getInstance().currentUser!!.uid
        data["profile_image"] = uri
        data["username"] = username



        db.collection("Users").document(FirebaseAuth.getInstance().currentUser!!.uid)
            .set(data, SetOptions.merge())
//            .setOption.merge
            .addOnCompleteListener {
                // Toast.makeText(this, "Working", Toast.LENGTH_SHORT).show()
                Log.e("TAG", "save: true")
            }
            .addOnFailureListener { e ->
                Log.e("TAG", "save: error $e")

                //                Toast.makeText(this, "Fail $e", Toast.LENGTH_SHORT).show()
            }
    }


}

fun removeUser(){
    val user = Firebase.auth.currentUser!!

    user.delete()
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d("TAG", "User account deleted.")
            }
        }
}

