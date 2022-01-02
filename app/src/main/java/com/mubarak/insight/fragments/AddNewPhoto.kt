package com.mubarak.insight.fragments

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

import com.firebase.ui.auth.AuthUI
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.mubarak.insight.R
import com.mubarak.insight.databinding.FragmentAddBinding
import com.mubarak.insight.viewmodel.SaveFirebase
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_start_page.*
import java.util.*

private const val PICK_PHOTO_CODE = 1234

class AddNewPhoto : Fragment() {
    private val PICK_IMAGE_REQUEST = 71
    private var filePath: Uri? = null
    private var firebaseStore: FirebaseStorage? = null
    private var storageReference: StorageReference? = null
    var binding: FragmentAddBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        storageReference = FirebaseStorage.getInstance().reference
//
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
//        binding?.upload?.setOnClickListener {
//            Log.e("GGG", "upload:${uploadFile()}")
//            uploadFile()
//            save(
//                binding!!.titleImage.editText?.text.toString(),
////                binding!!.overview.editText?.text.toString()
//            )
//            Log.e("GGG", "upload:${uploadFile()}")
//        }
    }


    private fun chooser() {
        val i = Intent(Intent.ACTION_GET_CONTENT)
        i.type = "image/*"
        i.action = Intent.ACTION_GET_CONTENT
        if (i != null) {
            startActivityForResult(i, PICK_PHOTO_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && data != null) {
            filePath = data.data!!
            binding?.chosenImage?.setImageURI(filePath)

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
                        val downloadUri = task.result
                        save(
                            downloadUri.toString(),
                            System.currentTimeMillis(),
                            title_image_input.text.toString()
                        )
                    } else {
                        // Handle failures
                    }
                }?.addOnFailureListener {

                }
        }

    }
}