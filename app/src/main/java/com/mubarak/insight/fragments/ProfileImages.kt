package com.mubarak.insight.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mubarak.insight.databinding.FragmentProfileFavoriteBinding
import com.mubarak.insight.databinding.FragmentProfileImagesBinding


class ProfileImages : Fragment() {

    private var binding: FragmentProfileImagesBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentProfileImagesBinding = FragmentProfileImagesBinding.inflate(inflater,container,false)
        binding = fragmentProfileImagesBinding
        return fragmentProfileImagesBinding.root
    }

}