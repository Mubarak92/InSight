package com.mubarak.insight.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mubarak.insight.binding.bindImage
import com.mubarak.insight.databinding.FragmentOverviewBinding

class Overview : Fragment() {

    var binding: FragmentOverviewBinding? = null
    private val viewModel: com.mubarak.insight.viewmodel.ViewModel by viewModels()
    lateinit var photos: String
    lateinit var title: String
    lateinit var creationTime: String
    lateinit var overview: String
    lateinit var link: String
    lateinit var arg: String
lateinit var imageUrl:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
arguments.let {
    arguments?.let {

        title = it.getString("Title").toString()
        imageUrl = it.getString("imageUrl").toString()
        creationTime = it.getString("creationTime").toString()
        overview = it.getString("overview").toString()
        link = it.getString("link").toString()


    }
}

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding?.showImg?.bindImage(viewModel.photos.value)
        arguments.let {
arg = it!!.getString("overview").toString()
        }

//        viewModel.imageInfo(position)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentOverviewBinding.inflate(inflater)

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = viewLifecycleOwner
//
//         Giving the binding access to the OverviewViewModel
        binding.viewModel = viewModel
//
//         Sets the adapter of the photosGrid RecyclerView
//        binding.photosGrid.adapter = MovieGridAdapter()

        return binding.root

    }

}