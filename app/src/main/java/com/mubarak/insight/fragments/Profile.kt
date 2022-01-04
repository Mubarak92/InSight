package com.mubarak.insight.fragments

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.mubarak.insight.adapters.MainPageAdapter
import com.mubarak.insight.databinding.FragmentProfileBinding
import com.mubarak.insight.viewmodel.ViewModel

class Profile : Fragment() {

    private lateinit var firestoreDb: FirebaseFirestore
    private var binding: FragmentProfileBinding? = null
    private lateinit var mAuth: FirebaseAuth
    private val viewModel: ViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        firestoreDb = FirebaseFirestore.getInstance()
        val postRef = firestoreDb.collection("images")
        postRef.addSnapshotListener { snapshot, e ->
            for (documents in snapshot!!.documents) {
                Log.e("TAG", "Docmmmmmmmmmmmm ${documents.id}: ${documents.data?.values}")
            }
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentProfileBinding = FragmentProfileBinding.inflate(inflater, container, false)

        binding = fragmentProfileBinding
        return binding?.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding?.rvProfile?.adapter = MainPageAdapter()

        binding?.lifecycleOwner= viewLifecycleOwner

        binding?.viewModel = viewModel


    }


}