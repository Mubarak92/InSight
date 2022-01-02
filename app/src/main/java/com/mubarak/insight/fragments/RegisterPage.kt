package com.mubarak.insight.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.mubarak.insight.R
import com.mubarak.insight.databinding.FragmentRegisterPageBinding


class RegisterPage : Fragment() {
    private var binding: FragmentRegisterPageBinding? = null
//    private val binding get() = _binding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentRegisterPageBinding = FragmentRegisterPageBinding.inflate(inflater, container, false)
        binding = fragmentRegisterPageBinding
        return fragmentRegisterPageBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

binding?.registerBtn?.setOnClickListener {
    register()
}
//        googleSignInClient = GoogleSignIn.getClient(requireContext(), gso)
//binding?.signOut?.setOnClickListener {


//        auth.signInWithEmailAndPassword(username, password).addOnCompleteListener { task ->
//            if (task.isSuccessful) {
//                goMainPage()
//            }
//        }


    }

    fun register() {
        val username = binding?.emailInput?.editableText.toString()
        val password = binding?.passwordInput?.editableText.toString()

        if (username.isNotEmpty() && password.isNotEmpty()) {
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(username, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val firebaseUser: FirebaseUser = task.result.user!!
                        Toast.makeText(
                            this.requireContext(),
                            "you have been registered",
                            Toast.LENGTH_SHORT
                        ).show()
                        findNavController().navigate(R.id.action_registerPage_to_startingPage)
                    } else {
                        Toast.makeText(this.requireContext(), "Error", Toast.LENGTH_SHORT).show()

                    }

                }.addOnFailureListener {
                    println(it.message)
                }


        }
    }
}