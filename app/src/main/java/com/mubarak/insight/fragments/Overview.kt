package com.mubarak.insight.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mubarak.insight.binding.bindImage
import com.mubarak.insight.databinding.FragmentOverviewBinding
import com.mubarak.insight.databinding.FragmentProfileBinding
import kotlinx.android.synthetic.main.fragment_add.view.*
import kotlinx.android.synthetic.main.fragment_overview.*

class Overview : Fragment() {
    private val navigationArgs: OverviewArgs by navArgs()


    private var _binding: FragmentOverviewBinding? = null
    private val binding get() = _binding

    private val viewModel: com.mubarak.insight.viewmodel.ViewModel by activityViewModels()
    var position: Int = 0
    lateinit var getImages: String
    lateinit var title: String
    private var creationTime: Long = 0
    lateinit var overview: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val getTitle = navigationArgs.title
        val getImages = navigationArgs.imageUrl
        Glide.with(this).load(getImages).into(show_img)


        Log.e("TAG", "onViewCreated: ${getImages}")
        Log.e("TAG", "onViewCreated: ${getTitle}")
        binding?.titleOverview?.text = getTitle

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentOverviewBinding.inflate(inflater, container, false)
        binding?.apply {
            viewModel = this@Overview.viewModel
            lifecycleOwner = this@Overview.viewLifecycleOwner
            overView = this@Overview
        }
        return binding!!.root

    }


    companion object {

        const val IMAGE = "imageUrl"
        const val CREATION_TIME = "creation_time"
    }

    fun getImageTime(creationTime: Long) {


    }

}