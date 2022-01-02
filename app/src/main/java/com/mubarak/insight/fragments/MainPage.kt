package com.mubarak.insight.fragments

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.data.model.User
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ListResult
import com.google.firebase.storage.StorageReference
import com.mubarak.insight.R
import com.mubarak.insight.adapters.MainPageAdapter
import com.mubarak.insight.data.Images
import com.mubarak.insight.databinding.FragmentMainPageBinding


class MainPage : Fragment() {
    private var signedInUser : User? = null
    private var binding: FragmentMainPageBinding? = null
    private lateinit var firestoreDb: FirebaseFirestore
    private lateinit var imageList: MutableList<Images>
    lateinit var adapter: MainPageAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        imageList = mutableListOf()
        firestoreDb = FirebaseFirestore.getInstance()


//        reFreshApp()
    }

//    private fun reFreshApp() {
////        Toast.makeText(this,"ReFresh",Toast.LENGTH_SHORT).show
//            binding?.swipeToRefresh?.isRefreshing = false
//
//    }


    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val fragmentMainPageBinding = FragmentMainPageBinding.inflate(inflater, container, false)
//        val imageList: MutableList<Images> = mutableListOf()
        adapter = MainPageAdapter(imageList, this.requireContext())
        binding = fragmentMainPageBinding
        binding?.recyclerView?.adapter = adapter
//        binding?.recyclerView?.setHasFixedSize(true)
        binding?.recyclerView?.layoutManager = LinearLayoutManager(this.context)


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


            binding?.recyclerView?.adapter?.notifyDataSetChanged()

            for (image in imageList) {
                Log.e(TAG, "image ${image}")
            }

        }
        return fragmentMainPageBinding.root
//       this.EventChangeListener()

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        adapter = MainPageAdapter(this.requireContext(), images)


        val storage = FirebaseStorage.getInstance()
        val ref = storage.reference.child("images/")
        val imageList: MutableList<Images> = mutableListOf()
        val listAllTask: Task<ListResult> = ref.listAll()

//
//        listAllTask.addOnCompleteListener { result ->
//            val items: List<StorageReference> = result.result.items
//            items.forEachIndexed { index, item ->
//                item.downloadUrl.addOnSuccessListener {
//                    Log.d("Tag", "$it")
//                    imageList.add(Images(
//                        it.toString(),
//                        downloadUrlTask.result.toString(),
//                        System.currentTimeMillis(),
//                        signedInUser
//                    ))
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
//
//    private fun EventChangeListener(): ArrayList<Images> {
//        var eventsArrayList: ArrayList<Images> = ArrayList()
//        firestoreDb = FirebaseFirestore.getInstance()
//        firestoreDb.collection("images").orderBy("current_time", Query.Direction.DESCENDING)
//            .addSnapshotListener(object : EventListener<QuerySnapshot> {
//                override fun onEvent(
//                    value: QuerySnapshot?,
//                    error: FirebaseFirestoreException?
//                ) {
//                    if (error != null) {
//                        Log.e("Fire Store Error: ", error.message.toString())
//                        return
//                    }
//                    for (dc: DocumentChange in value?.documentChanges!!) {
//                        if (dc.type == DocumentChange.Type.ADDED) {
//                            eventsArrayList.add(dc.document.toObject(Images::class.java))
//                        }
//                    }
//                    binding?.recyclerView?.adapter?.notifyDataSetChanged()
//                }
//
//            })
//
//        return eventsArrayList
//
//    }
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
