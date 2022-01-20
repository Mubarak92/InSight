package com.mubarak.insight.fragments

import android.annotation.SuppressLint
import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.firebase.ui.auth.data.model.User
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ListResult
import com.mubarak.insight.adapters.MainPageAdapter
import com.mubarak.insight.classes.FirestoreClass
import com.mubarak.insight.data.Images
import com.mubarak.insight.databinding.FragmentProfileFavoriteBinding
import com.mubarak.insight.databinding.FragmentProfileImagesBinding
import com.mubarak.insight.viewmodel.ViewModel


class ProfileImages : Fragment() {

    private val viewModel: ViewModel by viewModels()
    private var signedInUser: User? = null
    private var binding: FragmentProfileImagesBinding? = null
    private lateinit var mFirestore: FirebaseFirestore
    private val storage = FirebaseStorage.getInstance()
    private val ref = storage.reference.child("images/")
    private val imageUrl: MutableList<Images> = mutableListOf()
    val listAllTask: Task<ListResult> = ref.listAll()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        mFirestore = FirebaseFirestore.getInstance()
        FirestoreClass().signInUser(this)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val fragmentProfileImagesBinding = FragmentProfileImagesBinding.inflate(inflater, container, false)
        binding = fragmentProfileImagesBinding

        binding?.profileImagesRecyclerView?.adapter = MainPageAdapter()

        binding?.lifecycleOwner = viewLifecycleOwner

        binding?.viewModel = viewModel


        return binding?.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        mFirestore.collection("images")
            .document(FirebaseAuth.getInstance().currentUser?.uid as String)
            .get().addOnSuccessListener { userSnapshot ->
                signedInUser = userSnapshot.toObject(User::class.java)
                Log.e(ContentValues.TAG, "signin: $signedInUser ")
            }.addOnFailureListener {
                Log.e(ContentValues.TAG, "onCreateView: Faild to signin")
            }

        val imageRef = mFirestore.collection("Users").document(FirebaseAuth.getInstance().currentUser!!.uid).collection("Images")
            .orderBy("creation_time", Query.Direction.DESCENDING)
        imageRef.addSnapshotListener { snapshot, e ->
            if (e != null || snapshot == null) {
                return@addSnapshotListener
            }
            val images = snapshot.toObjects(Images::class.java)
            imageUrl.addAll(images)

            val adapter = binding?.profileImagesRecyclerView?.adapter as MainPageAdapter
            adapter.submitList(imageUrl)

        }
    }

}