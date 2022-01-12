package com.mubarak.insight.fragments

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.firebase.ui.auth.data.model.User
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ListResult
import com.mubarak.insight.R
import com.mubarak.insight.classes.Firestore
import com.mubarak.insight.data.Images
import com.mubarak.insight.databinding.FragmentProfileBinding
import com.mubarak.insight.viewmodel.ViewModel

class Profile : Fragment() {
    private val viewModel: ViewModel by activityViewModels()
    private var signedInUser : User? = null
    private val storage = FirebaseStorage.getInstance()
    private val ref = storage.reference.child("images/")
    private val imageList: MutableList<Images> = mutableListOf()
    private lateinit var mFirestore: FirebaseFirestore
    private var binding: FragmentProfileBinding? = null
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mFirestore = FirebaseFirestore.getInstance()
        Firestore().signInUser(this)


    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentProfileBinding = FragmentProfileBinding.inflate(inflater, container, false)

        binding = fragmentProfileBinding
        return fragmentProfileBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mAuth = FirebaseAuth.getInstance()
        val currentUser = mAuth.currentUser
        binding?.username?.text = currentUser?.email
binding?.edit?.setOnClickListener{
    findNavController().navigate(R.id.action_profile3_to_profileEdit)
}
        binding?.lifecycleOwner= viewLifecycleOwner

        binding?.viewModel = viewModel


        mFirestore.collection("images").document(FirebaseAuth.getInstance().currentUser?.uid as String)
            .get().addOnSuccessListener { userSnapshot ->
                signedInUser = userSnapshot.toObject(User::class.java)
                Log.e(ContentValues.TAG, "signin: $signedInUser ", )
            }.addOnFailureListener {
                Log.e(ContentValues.TAG, "onCreateView: Faild to signin", )
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

        }



    }

}