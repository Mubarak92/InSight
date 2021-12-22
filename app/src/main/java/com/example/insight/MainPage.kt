package com.example.insight

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.insight.databinding.FragmentMainPageBinding
import com.example.insight.databinding.FragmentStartPageBinding
import com.google.firebase.auth.FirebaseAuth


class MainPage : Fragment() {
    private var binding: FragmentMainPageBinding? = null
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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



        binding?.Add!!.setOnClickListener {
            findNavController().navigate(R.id.action_mainPage_to_add2)

        }

    }
}