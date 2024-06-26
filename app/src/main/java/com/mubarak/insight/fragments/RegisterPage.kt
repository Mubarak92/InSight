package com.mubarak.insight.fragments

import android.app.ProgressDialog
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isNotEmpty
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mubarak.insight.R
import com.mubarak.insight.classes.FirestoreClass
import com.mubarak.insight.data.Users
import com.mubarak.insight.databinding.FragmentRegisterPageBinding
import kotlinx.android.synthetic.main.fragment_register_page.*


class RegisterPage : Fragment() {
    private var _binding: FragmentRegisterPageBinding? = null
    private val binding get() = _binding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentRegisterPageBinding =
            FragmentRegisterPageBinding.inflate(inflater, container, false)
        _binding = fragmentRegisterPageBinding
        return fragmentRegisterPageBinding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.registerBtn?.setOnClickListener {
            registerNewUser()
        }

//        binding?.progressbar?.currentDrawable

    }


    private fun validateRegister(username: String, email: String, password: String): Boolean {
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


        val username = binding?.usernameInput?.editableText.toString()
        val email = binding?.emailInput?.editableText.toString()
        val password = binding?.passwordInput?.editableText.toString()
        usernameReg?.error = getString(R.string.usernameError)

        emailReg?.error = getString(R.string.emailError)
        emailReg.error = null
        passwordReg?.error = getString(R.string.passwordError)
        passwordReg.error = null
        if (validateRegister(username, email, password)) {
            val pd = ProgressDialog(this.requireContext())

            pd.setTitle("Please wait")
            pd.show()
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->

                    if (task.isSuccessful) {
                        val user = Firebase.auth.currentUser


                        user!!.sendEmailVerification().addOnCompleteListener { task ->
                            if (task.isSuccessful){
                                findNavController().navigate(R.id.action_registerPage_to_startingPage)

                            }
                        }
                        val firebaseUser: FirebaseUser = task.result.user!!
////                        val registeredEmail = firebaseUser.email!!
                        val users = Users(firebaseUser.uid, username, email)
//


                        FirestoreClass().registerUserIntoFirestore(this, users)

                        pd.dismiss()
                    } else {
                        Toast.makeText(this.requireContext(), "error", Toast.LENGTH_SHORT).show()

                    }
                }.addOnFailureListener {
                    println(it.message)
                }


        }
    }

}