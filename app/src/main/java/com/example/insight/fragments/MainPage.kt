package com.example.insight.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.insight.adapters.MainPageAdapter
import com.example.insight.R
import com.example.insight.data.Images
import com.example.insight.databinding.FragmentMainPageBinding
import com.example.insight.viewmodel.ViewModel
import com.firebase.ui.auth.AuthUI
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ListResult
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.async
import kotlinx.coroutines.tasks.await
import retrofit2.http.POST


class MainPage : Fragment() {

    private val viewModel: ViewModel by activityViewModels()

    private var binding: FragmentMainPageBinding? = null
    private lateinit var posts: MutableList<MainPageAdapter>

    val storage = FirebaseStorage.getInstance()
    val ref = storage.reference.child("image")
//    val imageList: MutableList<Images> = mutableListOf()
    val listAllTask: Task<ListResult> = ref.listAll()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)


    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMainPageBinding.inflate(inflater)
        setHasOptionsMenu(true)


        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this

        // Giving the binding access to the OverviewViewModel
        binding.viewModel = viewModel

        // Sets the adapter of the photosGrid RecyclerView
        binding.recyclerView.adapter = MainPageAdapter()

        return binding.root


    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listAllTask.addOnCompleteListener { result ->
            val items: List<StorageReference> = result.result.items

            val li : MutableList<String> = mutableListOf()


            lifecycleScope.async {


                val l = async {
                    items.forEachIndexed { index, item ->

                        li.add(item.downloadUrl.await().toString())

                    }
                    return@async li
                }
                Log.d("TAG", "onViewCreated: ${l.await().toString()} ")
            }


        }
    }


                //                addOnSuccessListener {
//                    Log.e("TAG1", "$it")
//                    viewModel.photo.value?.plus(it)
//                    Log.e("TAG", "onViewCreated: ${viewModel.photo.value}", )
//                }.addOnCompleteListener {
////                    viewModel.photo= imageList.set()
//                    Log.e("TAG2", " size ${viewModel.photo.value?.size}")
//
//                    binding?.recyclerView?.adapter = MainPageAdapter()
//                    binding?.recyclerView?.layoutManager =
//                        LinearLayoutManager(this.requireContext())
//                }



    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.signout, menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.profile -> {
                if (item.itemId == R.id.profile)
                    findNavController().navigate(R.id.action_mainPage_to_profile2)
                true
            }
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
            else -> super.onOptionsItemSelected(item)
        }


    }
//
//    fun getImages() {
//        listAllTask.addOnCompleteListener { result ->
//            val items: List<StorageReference> = result.result.items
//            items.forEachIndexed { index, item ->
//                item.downloadUrl.await().path
//
//            }
//        }
//    }
}
