package com.mubarak.insight.fragments

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.firebase.ui.auth.data.model.User
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ListResult
import com.mubarak.insight.R
import com.mubarak.insight.activitys.NavActivity
import com.mubarak.insight.adapters.ProfilePageAdapter
import com.mubarak.insight.data.Images
import com.mubarak.insight.databinding.FragmentProfileFavoriteBinding
import com.mubarak.insight.databinding.FragmentResetPasswordBinding
import com.mubarak.insight.viewmodel.ViewModel

class ResetPasswordFragment : Fragment() {
    private val viewModel: ViewModel by viewModels()
    private var signedInUser: User? = null
    private var binding: FragmentResetPasswordBinding? = null
    private lateinit var mFirestore: FirebaseFirestore
    private val storage = FirebaseStorage.getInstance()
    private val ref = storage.reference.child("images/")
    private val imageUrl: MutableList<Images> = mutableListOf()
    val listAllTask: Task<ListResult> = ref.listAll()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        mFirestore = FirebaseFirestore.getInstance()
    }


    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val fragmentResetPasswordBinding = FragmentResetPasswordBinding.inflate(inflater, container, false)
        binding = fragmentResetPasswordBinding

        return binding?.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        fun validate(email: String): Boolean {
            return when {

                TextUtils.isEmpty(email) -> {
                    Toast.makeText(this.requireContext(), "Please your Email", Toast.LENGTH_SHORT)
                        .show()
                    false
                }

                else -> true
            }

        }


        fun resetPassword() {
            val pd = ProgressDialog(this.requireContext())
            pd.setTitle("Please Wait")
            pd.show()
            val email = binding?.emailInputs?.editableText.toString()
            if (validate(email)) {
                Firebase.auth.sendPasswordResetEmail(email)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            pd.dismiss()

//                            val firebaseUser: FirebaseUser = task.result.user!!
//                            val loggedInUser = task.toObject(Users::class.java)!!



                            Toast.makeText(
                                this.requireContext(),
                                "Email sent",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                            findNavController().navigate(R.id.action_Reset_to_Login)
                        } else {
                            Toast.makeText(
                                this.requireContext(),
                                "Error",
                                Toast.LENGTH_SHORT
                            ).show()

                        }
                    }
                    .addOnFailureListener {
                        println(it.message)
                    }
            }
        }
        binding?.resetPassword?.setOnClickListener {
            resetPassword()
        }
        }

}