package com.mubarak.insight.fragments

import android.app.Activity
import android.app.ProgressDialog
import android.app.appsearch.AppSearchResult.RESULT_OK
import android.content.ActivityNotFoundException
import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

import com.firebase.ui.auth.AuthUI
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.mubarak.insight.R
import com.mubarak.insight.data.Images
import com.mubarak.insight.databinding.FragmentAddBinding
import com.mubarak.insight.viewmodel.SaveFirebase
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_overview.*
import kotlinx.android.synthetic.main.fragment_start_page.*
import java.util.*

private const val PICK_PHOTO_CODE = 1234
val REQUEST_IMAGE_CAPTURE = 1
class AddNewPhoto : Fragment() {
    private var filePath: Uri? = null
    private val mFirestore = FirebaseFirestore.getInstance()
    private var storageReference: StorageReference? = null
    var binding: FragmentAddBinding? = null
//    private lateinit var images:MutableList<Images>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        storageReference = FirebaseStorage.getInstance().reference

//        firestoreDb.collection("users").document(FirebaseAuth.getInstance().currentUser?.uid as String)
//            .get().addOnSuccessListener { userSnapshot ->
//                signedinUser = userSnapshot.toObject(User::class.java)
//                Log.e(ContentValues.TAG, "signin: $signedinUser ", )
//            }.addOnFailureListener {
//                Log.e(ContentValues.TAG, "onCreateView: Faild to signin", )
//            }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentBinding = FragmentAddBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root

        // return super.onCreateView(inflater, container, savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.choose?.setOnClickListener {
            chooser()
        }

        binding?.upload?.setOnClickListener {
            uploadFile()
        }
        binding?.camera?.setOnClickListener {
            camera()
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

    private fun camera() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        } catch (e: ActivityNotFoundException) {
            // display error state to the user
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && data != null ) {
            filePath = data.data!!
            binding?.ivImage?.setImageURI(filePath)
//            val imageBitmap = data.extras?.get(filePath.toString()) as Bitmap
//            imageView.setImageBitmap(imageBitmap)

        } else {
            Toast.makeText(this.requireContext(), "Image pick canceled", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.signout, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.signOutMenu -> {
                AuthUI.getInstance()
                    .signOut(this.requireContext())
                findNavController().navigate(R.id.action_add2_to_loginPage)

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    private fun save(uri: String, systemTime: Long = System.currentTimeMillis(), title: String) {

        SaveFirebase().save(uri, systemTime, title)
    }


    private fun uploadFile() {
//         val filePath: Uri? = null
//
//        val imageRef =
//                FirebaseStorage.getInstance().reference.child("images/${System.currentTimeMillis()}-Photo.jpg")
////            val uploadTask = imageRef?.putFile(filePath!!)
//        imageRef.putFile(filePath)
//            .continueWithTask { photoUploadTask ->
//                imageRef.downloadUrl
//            }.continueWithTask { downloadUrlTask ->
//                val post = Images (
//                    title.text.toString(),
//                    downloadUrlTask.result.toString(),
//                    System.currentTimeMillis(),
//                    signedInUser)
//                mFirestore.collection("Images").add(post)
//            }.addOnCompletelistener { postCreationTask ->
//
//                if (!postCreationTask.isSuccessful) {
//
//
//
        val pd = ProgressDialog(this.requireContext())
        pd.setTitle("Uploading")
        pd.show()
        if (filePath != null) {

            val imageRef =
                FirebaseStorage.getInstance().reference.child("images/${System.currentTimeMillis()}-Photo.jpg\"")
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

                        pd.dismiss()
                        Toast.makeText(this.requireContext(), "Photo uploaded successfully", Toast.LENGTH_SHORT).show()
                        val downloadUri = task.result
                        save(
                            downloadUri.toString(),
                            System.currentTimeMillis(),
                            title_image_input.text.toString()
                        )

                        Log.e("TAG","massage:$filePath")} else {
                        // Handle failures
                    }
                }?.addOnFailureListener {

                }
        }

    }
}