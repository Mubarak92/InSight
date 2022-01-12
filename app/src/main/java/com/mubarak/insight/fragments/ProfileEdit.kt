package com.mubarak.insight.fragments

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ProgressDialog
import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.firebase.ui.auth.data.model.User
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ListResult
import com.google.firebase.storage.UploadTask
import com.mubarak.insight.adapters.MainPageAdapter
import com.mubarak.insight.data.Images
import com.mubarak.insight.databinding.FragmentMainPageBinding
import com.mubarak.insight.databinding.FragmentProfileEditBinding
import com.mubarak.insight.viewmodel.DeleteFirebase
import com.mubarak.insight.viewmodel.EditFirebase
import com.mubarak.insight.viewmodel.SaveFirebase
import com.mubarak.insight.viewmodel.ViewModel
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_profile_edit.*

private const val PICK_PHOTO_CODE = 123

class ProfileEdit : Fragment() {

    var binding: FragmentProfileEditBinding? = null

    private val viewModel: ViewModel by activityViewModels()
    private var signedInUser: User? = null
    private var filePath: Uri? = null
    private lateinit var mFirestore: FirebaseFirestore
    private val storage = FirebaseStorage.getInstance()
    private val ref = storage.reference.child("images/")
    private val imageList: MutableList<Images> = mutableListOf()
    val listAllTask: Task<ListResult> = ref.listAll()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        mFirestore = FirebaseFirestore.getInstance()

//        Firestore().signInUser(this)


    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val fragmentProfileEditBinding =
            FragmentProfileEditBinding.inflate(inflater, container, false)
        binding = fragmentProfileEditBinding


        return fragmentProfileEditBinding?.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.saveChanges?.setOnClickListener {
            editProfile()
        }
        binding?.profileImage?.setOnClickListener {
            chooser()
        }
        binding?.delete?.setOnClickListener {
            delete()
        }
        binding?.lifecycleOwner = viewLifecycleOwner

        mFirestore.collection("images")
            .document(FirebaseAuth.getInstance().currentUser?.uid as String)
            .get().addOnSuccessListener { userSnapshot ->
                signedInUser = userSnapshot.toObject(User::class.java)
                Log.e(ContentValues.TAG, "signin: $signedInUser ")
            }.addOnFailureListener {
                Log.e(ContentValues.TAG, "onCreateView: Faild to signin")
            }

        val imageRef = mFirestore.collection("images").limit(20)
            .orderBy("creation_time", Query.Direction.DESCENDING)
        imageRef.addSnapshotListener { snapshot, e ->
            if (e != null || snapshot == null) {
                Log.e(ContentValues.TAG, "exception when query post", e)
                return@addSnapshotListener
            }
            val images = snapshot.toObjects(Images::class.java)
            imageList.addAll(images)


            for (image in imageList) {
                Log.e(ContentValues.TAG, "image ${image}")
            }
//
//            var adapter= binding?.recyclerView?.adapter as MainPageAdapter
//            adapter.submitList(imageList)

        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && data != null) {
            filePath = data.data!!
//            binding??.setImageURI(filePath)
        }
    }

    private fun chooser() {
        val i = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        i.type = "image/*"
        i.action = Intent.ACTION_GET_CONTENT
        if (i != null) {
            startActivityForResult(i, PICK_PHOTO_CODE)
        }
    }

    private fun edit(uri: String, title: String) {

        EditFirebase().edit(uri, title)
    }

    private fun delete() {

        DeleteFirebase().delete()
    }

    private fun editProfile() {

//        val pd = ProgressDialog(this.requireContext())
//        pd.setTitle("Uploading")
//        pd.show()
        if (filePath != null) {

            val imageRef =
                FirebaseStorage.getInstance().reference.child("profiles/${System.currentTimeMillis()}-Photo.jpg\"")
            val uploadTask = imageRef?.putFile(filePath!!)

            val urlTask =
                uploadTask.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
                    if (!task.isSuccessful) {
                        task.exception?.let {
                            throw it
                        }
                    }
                    return@Continuation imageRef.downloadUrl
                })?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {

//                        pd.dismiss()
                        Toast.makeText(this.requireContext(), "Profile edited", Toast.LENGTH_SHORT)
                            .show()
                        val downloadUri = task.result
                        edit(
                            downloadUri.toString(),
                            usernameInput.text.toString()
                        )

                        Log.e("TAG", "massage:$filePath")
                    } else {
                        // Handle failures
                    }
                }?.addOnFailureListener {

                }
        }

    }


}