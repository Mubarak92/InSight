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
import com.mubarak.insight.fragments.Profile
import com.mubarak.insight.fragments.RegisterPage
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.coroutines.launch
import java.lang.Exception
import java.util.HashMap

private const val PICK_PHOTO_CODE = 1234

enum class InSightStatus { LOADING, ERROR, DONE }


class ViewModel : ViewModel() {




    private val _photos = MutableLiveData<List<Images>?>()
    val photos: MutableLiveData<List<Images>?> = _photos

    private val _status = MutableLiveData<InSightStatus>()

    // The external immutable LiveData for the request status
    val status: LiveData<InSightStatus> = _status


    private val imageLink = MutableLiveData<String>()


    private val _username = MutableLiveData<Images>()
    val username: MutableLiveData<Images> = _username

//    init {
//        getPhotos()
//    }

//    fun getPhotos() {
//        viewModelScope.launch {
//            try {
//
//
//            } catch (e: Exception) {
//
//            }
//        }
//    }


        private val mFirestore = FirebaseFirestore.getInstance()
//
//        fun registerUserIntoFirestore(
//            fragment: RegisterPage,
//            userInfo: Users
//        ) {
//            mFirestore.collection("Users").document(getUserId())
//                .set(userInfo, SetOptions.merge())
//                .addOnCompleteListener {
//                    fragment.userRegistresSuccess(Users)
//                }
//        }
//
//        fun signInUser(fragment: Fragment) {
//            Log.e("TAG", "signInUser: userId = ${getUserId()}", )
//            // Here we pass the collection name from which we wants the data.
//            var a= mFirestore.collection("Users")
//                .document(getUserId())
//
//            Log.e("TAG", "signInUser: a= $a", )
//
//            a.get()
//                .addOnSuccessListener { userSnapshot ->
//                    Log.e("TAG", "signInUser: userSnapshot=  $userSnapshot", )
//                    val loggedInUser = userSnapshot.toObject(Users::class.java)!!
//
//
//                    when (fragment) {
//                        is LoginPage -> {
//                            fragment.signInSuccess(loggedInUser)
//                        }
//                        is Profile -> {
//                            fragment.updateUserDetails(loggedInUser)
//                        }
//                        // END
//                    }
//                    // END
//                }
//                .addOnFailureListener { e ->
//                }
//
//        }

        fun getUserId(): String {
            val currentUser = FirebaseAuth.getInstance().currentUser!!

            // A variable to assign the currentUserId if it is not null or else it will be blank.
            var currentUserID = ""
            if (currentUser != null) {
                currentUserID = currentUser.uid
            }

            return currentUserID
        }
    }



//
//    fun imageInfo(index: Int) {
//        val item = _photos.value?.get(index)
//        Log.e("image","Before")
//       imageLink.value = item?.image_url
//        Log.e("image"," ${imageLink.value}")
//
//        Log.e("image","After")
//
//    }





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

