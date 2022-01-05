package com.mubarak.insight.fragments

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.mubarak.insight.R
import com.mubarak.insight.classes.Firestore
import com.mubarak.insight.data.Users
import com.mubarak.insight.databinding.FragmentRegisterPageBinding


class RegisterPage : Fragment() {
    private var binding: FragmentRegisterPageBinding? = null
//    private val binding get() = _binding



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentRegisterPageBinding =
            FragmentRegisterPageBinding.inflate(inflater, container, false)
        binding = fragmentRegisterPageBinding
        return fragmentRegisterPageBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.registerBtn?.setOnClickListener {
            registerNewUser()
        }

//        binding?.progressbar?.currentDrawable

    }

    private fun validate(username: String, email: String, password: String): Boolean {
        return when {

            TextUtils.isEmpty(username) -> {
                Toast.makeText(this.requireContext(), "Please Enter Username", Toast.LENGTH_SHORT)
                    .show()
                false
            }
            TextUtils.isEmpty(email) -> {
                Toast.makeText(this.requireContext(), "Please your Email", Toast.LENGTH_SHORT)
                    .show()
                false
            }
            TextUtils.isEmpty(password) -> {
                Toast.makeText(this.requireContext(), "Please Enter Password", Toast.LENGTH_SHORT)
                    .show()
                false
            }
            else -> true
        }

    }

    fun userRegistresSuccess(Users: Users.CREATOR) {
        Toast.makeText(
            this.requireContext(),
            "you have been successfully registered",
            Toast.LENGTH_LONG
        ).show()
//        pd.currentDrawable.


    }


     fun registerNewUser() {
//   pd.setTitle("Please wait")
//        pd.show()
//

        val username = binding?.usernameInput?.editableText.toString()
        val email = binding?.emailInput?.editableText.toString()
        val password = binding?.passwordInput?.editableText.toString()

        if(validate(username, email, password)){

            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
//                    pd.dismiss()

                    if (task.isSuccessful) {
                        val firebaseUser: FirebaseUser = task.result.user!!
//                        val registeredEmail = firebaseUser.email!!
                        val users = Users(firebaseUser.uid, username, email)



                        Firestore().registerUserIntoFirestore( this, users)
                        findNavController().navigate(R.id.action_registerPage_to_startingPage)

                    } else {
                        Toast.makeText(this.requireContext(), "error", Toast.LENGTH_SHORT).show()

                    }
                }.addOnFailureListener {
                    println(it.message)
                }


        }
    }



}