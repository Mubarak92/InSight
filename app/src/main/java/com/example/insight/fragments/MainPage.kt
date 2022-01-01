package com.example.insight.fragments

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.insight.MainPageAdapter
import com.example.insight.R
import com.example.insight.data.Images
import com.example.insight.databinding.FragmentMainPageBinding
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot


class MainPage : Fragment() {
    private var binding: FragmentMainPageBinding? = null
    private lateinit var firestoreDb: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentMainPageBinding = FragmentMainPageBinding.inflate(inflater, container, false)

        binding = fragmentMainPageBinding
        return fragmentMainPageBinding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imageList: MutableList<Images> = mutableListOf()
//        adapter = MainPageAdapter(this.requireContext(), images)
        firestoreDb = FirebaseFirestore.getInstance()
        val postRef = firestoreDb.collection("images").limit(20)
            .orderBy("creation_time", Query.Direction.DESCENDING)
        postRef.addSnapshotListener { snapshot, e ->
            if (e != null || snapshot == null) {
                Log.e(TAG, "exception when query post", e)
                return@addSnapshotListener
            }
            var imageList = snapshot.toObjects(Images::class.java)
            for (image in imageList) {
                Log.e(TAG, "image ${image}")
            }

        }

        binding?.recyclerView?.adapter =
            MainPageAdapter(imageList, this.requireContext())
        binding?.recyclerView?.layoutManager =
            LinearLayoutManager(this.requireContext())


//        val storage = FirebaseStorage.getInstance()
//        val ref = storage.reference.child("image")
//        val imageList: MutableList<Images> = mutableListOf()
//        val listAllTask: Task<ListResult> = ref.listAll()
//
//
//        listAllTask.addOnCompleteListener { result ->
//            val items: List<StorageReference> = result.result.items
//            items.forEachIndexed { index, item ->
//                item.downloadUrl.addOnSuccessListener {
//                    Log.d("Tag", "$it")
//                    imageList.add(Images(it.toString(),it.t,it))
//                }.addOnCompleteListener {
//                    binding?.recyclerView?.adapter =
//                        MainPageAdapter(imageList, this.requireContext())
//                    binding?.recyclerView?.layoutManager =
//                        LinearLayoutManager(this.requireContext())
//                }
//            }
//        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.signout, menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.signOutMenu -> {
                AuthUI.getInstance()
                    .signOut(this.requireContext())
                findNavController().navigate(R.id.action_mainPage_to_loginPage)

                true
            }
            R.id.add -> {

                findNavController().navigate(R.id.action_mainPage_to_add2)
                true
            }
            R.id.profile -> {

                findNavController().navigate(R.id.action_mainPage_to_profile2)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }


    }
}

//
//
//
//package com.example.insight.fragments
//
//import android.os.Bundle
//import android.util.Log
//import android.view.*
//import androidx.fragment.app.Fragment
//import androidx.fragment.app.activityViewModels
//import androidx.lifecycle.lifecycleScope
//import androidx.navigation.fragment.findNavController
//import com.example.insight.adapters.MainPageAdapter
//import com.example.insight.R
//import com.example.insight.databinding.FragmentMainPageBinding
//import com.example.insight.viewmodel.ViewModel
//import com.firebase.ui.auth.AuthUI
//import com.google.android.gms.tasks.Task
//import com.google.firebase.storage.FirebaseStorage
//import com.google.firebase.storage.ListResult
//import com.google.firebase.storage.StorageReference
//import kotlinx.coroutines.async
//import kotlinx.coroutines.tasks.await
//
//
//class MainPage : Fragment() {
//
//    private val viewModel: ViewModel by activityViewModels()
//
//    private var binding: FragmentMainPageBinding? = null
//    private lateinit var posts: MutableList<MainPageAdapter>
//
//    val storage = FirebaseStorage.getInstance()
//    val ref = storage.reference.child("image")
//    //    val imageList: MutableList<Images> = mutableListOf()
//    val listAllTask: Task<ListResult> = ref.listAll()
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setHasOptionsMenu(true)
//
//
//    }
//
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        val binding = FragmentMainPageBinding.inflate(inflater)
//        setHasOptionsMenu(true)
//
//
//        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
//        binding.lifecycleOwner = this
//
//        // Giving the binding access to the OverviewViewModel
//        binding.viewModel = viewModel
//
//        // Sets the adapter of the photosGrid RecyclerView
//        binding.recyclerView.adapter = MainPageAdapter()
//
//        return binding.root
//
//
//    }
//
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        listAllTask.addOnCompleteListener { result ->
//            val items: List<StorageReference> = result.result.items
//
//            val li : MutableList<String> = mutableListOf()
//
//
//            lifecycleScope.async {
//
//
//                val l = async {
//                    items.forEachIndexed { index, item ->
//
//                        li.add(item.downloadUrl.await().toString())
//
//                    }
//                    return@async li
//                }
//                Log.d("TAG", "onViewCreated: ${l.await().toString()} ")
//            }
//
//
//        }
//    }
//
//
//    //                addOnSuccessListener {
////                    Log.e("TAG1", "$it")
////                    viewModel.photo.value?.plus(it)
////                    Log.e("TAG", "onViewCreated: ${viewModel.photo.value}", )
////                }.addOnCompleteListener {
//////                    viewModel.photo= imageList.set()
////                    Log.e("TAG2", " size ${viewModel.photo.value?.size}")
////
////                    binding?.recyclerView?.adapter = MainPageAdapter()
////                    binding?.recyclerView?.layoutManager =
////                        LinearLayoutManager(this.requireContext())
////                }
//
//
//
//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        inflater.inflate(R.menu.signout, menu)
//    }
//
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        return when (item.itemId) {
//            R.id.profile -> {
//                if (item.itemId == R.id.profile)
//                    findNavController().navigate(R.id.action_mainPage_to_profile2)
//                true
//            }
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
//            else -> super.onOptionsItemSelected(item)
//        }
//
//
//    }
////
////    fun getImages() {
////        listAllTask.addOnCompleteListener { result ->
////            val items: List<StorageReference> = result.result.items
////            items.forEachIndexed { index, item ->
////                item.downloadUrl.await().path
////
////            }
////        }
////    }
//}
