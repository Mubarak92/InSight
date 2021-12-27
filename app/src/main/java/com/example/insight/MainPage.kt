package com.example.insight

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.insight.data.Item
import com.example.insight.databinding.FragmentMainPageBinding
import com.example.insight.databinding.FragmentStartPageBinding
import com.firebase.ui.auth.AuthUI
import com.google.android.gms.tasks.Task
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ListResult
import com.google.firebase.storage.StorageReference
import java.sql.Ref
import kotlin.math.log


class MainPage : Fragment() {
    private var binding: FragmentMainPageBinding? = null
    private lateinit var auth: FirebaseAuth


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

        val storage = FirebaseStorage.getInstance()
        val ref = storage.reference.child("image")
        val imageList: MutableList<Item> = mutableListOf()
        val listAllTask: Task<ListResult> = ref.listAll()


        listAllTask.addOnCompleteListener { result ->
            val items: List<StorageReference> = result.result.items
            items.forEachIndexed { index, item ->
                item.downloadUrl.addOnSuccessListener {
                    Log.d("Tag", "$it")
                    imageList.add(Item(it.toString()))
                }.addOnCompleteListener {
                    binding?.recyclerView?.adapter = ImageAdapter(imageList, this.requireContext())
                    binding?.recyclerView?.layoutManager =
                        LinearLayoutManager(this.requireContext())
                }
            }
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


}