package com.mubarak.insight.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mubarak.insight.R
import com.mubarak.insight.databinding.FragmentStartPageBinding

class StartingPage : Fragment() {
    private var binding: FragmentStartPageBinding? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentStartPageBinding = FragmentStartPageBinding.inflate(inflater, container, false)

        binding = fragmentStartPageBinding
        return fragmentStartPageBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.Login!!.setOnClickListener {
            findNavController().navigate(R.id.action_startingPage_to_loginPage)

        }

        binding?.Register?.setOnClickListener {
            findNavController().navigate(R.id.main_to_re)
        }

    }
}