package com.mubarak.insight.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mubarak.insight.binding.bindImage
import com.mubarak.insight.databinding.FragmentOverviewBinding

class Overview : Fragment() {

    var binding: FragmentOverviewBinding? = null
    private val viewModel: com.mubarak.insight.viewmodel.ViewModel by activityViewModels()
    var position: Int = 0
    lateinit var getImages: String
    lateinit var title: String
    private var creationTime: Long = 0
    lateinit var overview: String
    lateinit var link: String
    lateinit var arg: String
    private lateinit var imageUrl: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        arguments.let {
            Log.e("TAG", "onCreate: title ${it?.getString("creation_time").toString()}")
            getImages = it?.getString(IMAGE).toString()
            creationTime = it?.getLong(CREATION_TIME)!!
            Log.e("TAG", "onCreate: CREATION_TIME ${it?.getString("creation_time").toString()}")

        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding?.showImg?.bindImage(viewModel.creationTime.value)
        arguments?.let {
            Log.e("TAG", "onViewCreated: ${it.getString("creation_time").toString()}")
            position = it.getLong("creation_time").toInt()
        }

        viewModel.imageInfo(position.toString())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentOverviewBinding.inflate(inflater)

        binding?.apply {
            viewModel = this@Overview.viewModel
            lifecycleOwner = this@Overview.viewLifecycleOwner
            overView = this@Overview
        }
        return binding.root

    }


    companion object {

        const val IMAGE = "imageUrl"
        const val CREATION_TIME = "creation_time"
    }

    fun getImageTime(creationTime: Long) {

        Firebase.firestore.collection("images").whereEqualTo("creation_time", creationTime)
            .get().addOnCompleteListener(OnCompleteListener<QuerySnapshot?> { task ->
                if (task.isSuccessful) {
                    for (documentSnapshot in task.result.documents) {
                        viewModel.creationTime.value =
                            documentSnapshot.data?.get("creation_time").toString()
                    }
                } else {

                }

            })
    }

}