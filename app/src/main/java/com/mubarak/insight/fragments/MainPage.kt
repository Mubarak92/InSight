package com.mubarak.insight.fragments

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.data.model.User
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ListResult
import com.mubarak.insight.R
import com.mubarak.insight.adapters.MainPageAdapter
import com.mubarak.insight.data.Images
import com.mubarak.insight.databinding.FragmentMainPageBinding
import com.mubarak.insight.viewmodel.ViewModel


class MainPage : Fragment() {
    private val viewModel: ViewModel by activityViewModels()
    private var signedInUser : User? = null
    private var binding: FragmentMainPageBinding? = null
    private lateinit var firestoreDb: FirebaseFirestore
    private val storage = FirebaseStorage.getInstance()
    private val ref = storage.reference.child("images/")
    private val imageList: MutableList<Images> = mutableListOf()
    val listAllTask: Task<ListResult> = ref.listAll()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        firestoreDb = FirebaseFirestore.getInstance()
    }




    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val fragmentMainPageBinding = FragmentMainPageBinding.inflate(inflater, container, false)
        binding = fragmentMainPageBinding


        return binding?.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.recyclerView?.adapter = MainPageAdapter()

        binding?.lifecycleOwner= viewLifecycleOwner

        binding?.viewModel = viewModel


        firestoreDb.collection("images").document(FirebaseAuth.getInstance().currentUser?.uid as String)
            .get().addOnSuccessListener { userSnapshot ->
                signedInUser = userSnapshot.toObject(User::class.java)
                Log.e(TAG, "signin: $signedInUser ", )
            }.addOnFailureListener {
                Log.e(TAG, "onCreateView: Faild to signin", )
            }

        val imageRef = firestoreDb.collection("images").limit(20)
            .orderBy("creation_time", Query.Direction.DESCENDING)
        imageRef.addSnapshotListener { snapshot, e ->
            if (e != null || snapshot == null) {
                Log.e(TAG, "exception when query post", e)
                return@addSnapshotListener
            }
            val images = snapshot.toObjects(Images::class.java)
            imageList.addAll(images)


            for (image in imageList) {
                Log.e(TAG, "image ${image}")
            }

            var adapter= binding?.recyclerView?.adapter as MainPageAdapter
            adapter.submitList(imageList)

        }



    }

//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        inflater.inflate(R.menu.signout, menu)
//    }
//
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        return when (item.itemId) {
//            R.id.signOutMenu -> {
//                AuthUI.getInstance()
//                    .signOut(this.requireContext())
//                findNavController().navigate(R.id.action_mainPage_to_loginPage)
//
//                true
//            }
//            R.id.add -> {
//
//                findNavController().navigate(R.id.action_mainPage_to_add2)
//                true
//            }
//            R.id.profile -> {
//
//                findNavController().navigate(R.id.action_mainPage_to_profile2)
//                true
//            }
//            else -> super.onOptionsItemSelected(item)
//        }
//


}
