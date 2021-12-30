package com.example.insight.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.insight.R
import com.example.insight.databinding.FragmentLoginPageBinding
import com.example.insight.databinding.FragmentRegisterPageBinding
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth


class RegisterPage : Fragment() {
    private var _binding: FragmentRegisterPageBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterPageBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



//        googleSignInClient = GoogleSignIn.getClient(requireContext(), gso)
//binding?.signOut?.setOnClickListener {



        val username = binding?.username?.toString()
        val password = binding?.password?.toString()
        val password2 = binding?.rePassword.toString()
        val email = binding?.email.toString()

        if (username!!.isBlank() || password!!.isBlank() && password2.isBlank() || email.isBlank()) {
            Toast.makeText(this.requireContext(), "Please fill everything", Toast.LENGTH_SHORT)
                .show()
        }

//        auth.signInWithEmailAndPassword(username, password).addOnCompleteListener { task ->
//            if (task.isSuccessful) {
//                goMainPage()
//            }
//        }


    }


}