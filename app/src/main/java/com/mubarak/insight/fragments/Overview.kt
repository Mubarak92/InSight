package com.mubarak.insight.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.google.common.collect.Sets
import com.mubarak.insight.databinding.FragmentOverviewBinding

class Overview : Fragment() {

    private val viewModel: com.mubarak.insight.viewmodel.ViewModel by activityViewModels()
    var position: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        arguments.let {
            position = it!!.getInt("ImageId")
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel.imageInfo(position)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentOverviewBinding.inflate(inflater)


        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this
//
//         Giving the binding access to the OverviewViewModel
        binding.viewModel = viewModel
//
//         Sets the adapter of the photosGrid RecyclerView
//        binding.photosGrid.adapter = MovieGridAdapter()

        return binding.root

    }

}