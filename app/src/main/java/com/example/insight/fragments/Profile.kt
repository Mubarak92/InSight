package com.example.insight.fragments

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.insight.data.Images
import com.example.insight.databinding.FragmentProfileBinding
import com.example.insight.databinding.FragmentStartPageBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class Profile : Fragment() {

    private lateinit var firestoreDb: FirebaseFirestore
    private var binding: FragmentProfileBinding? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        firestoreDb = FirebaseFirestore.getInstance()
        val postRef = firestoreDb.collection("images").limit(20).orderBy("creation_time",Query.Direction.DESCENDING)
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
        return fragmentProfileBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        Glide.with(requireContext()).load(image_url).into(holder.imageView)
//     binding?.profileImage?.setImageURI("https://firebasestorage.googleapis.com/v0/b/insight-cc302.appspot.com/o/image%2FMon%20Dec%2027%2011%3A47%3A23%20GMT%2B03%3A00%202021.jpg?alt=media&token=bee69eab-06c4-458c-bf23-df107bf3dca9".toUri())

    }


}