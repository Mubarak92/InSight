package com.mubarak.insight.fragments

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.firebase.ui.auth.data.model.User
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mubarak.insight.R
import com.mubarak.insight.activitys.NavActivity
import com.mubarak.insight.data.Users
import com.mubarak.insight.databinding.FragmentLoginPageBinding


class LoginPage : Fragment() {
    private var _binding: FragmentLoginPageBinding? = null
    private val binding get() = _binding

    companion object {
        private const val RC_SIGN_IN = 120
    }

    private lateinit var mAuth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    // ...
// Initialize Firebase Auth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.get_key))
            .requestEmail().build()
        googleSignInClient = GoogleSignIn.getClient(this.requireContext(), gso)

        mAuth = Firebase.auth


    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val exception = task.exception
            if (task.isSuccessful)
                try {
                    // Google Sign In was successful, authenticate with Firebase
                    val account = task.getResult(ApiException::class.java)!!
                    Log.d("TAG", "firebaseAuthWithGoogle:" + account.id)
                    firebaseAuthWithGoogle(account.idToken!!)
                } catch (e: ApiException) {
                    // Google Sign In failed, update UI appropriately
                    Log.w("TAG", "Google sign in failed", e)
                } else {
                Log.w("signIn", exception.toString())

            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this.requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("TAG", "signInWithCredential:success")
//                    findNavController().navigate(R.id.action_loginPage_to_mainPage)

                    activity?.let {
                        val intent = Intent(this.requireContext(), NavActivity::class.java)
                        this.startActivity(intent)

                    }
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("TAG", "signInWithCredential:failure", task.exception)
                }
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginPageBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val auth = FirebaseAuth.getInstance()


         fun validate(email: String, password: String): Boolean {
            return when {

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

//        fun login(){
//
//
//        }

        fun login() {

//            var pd = ProgressDialog(this.requireContext())
//            pd.setTitle("Please Wait")
//            pd.show()
            val email = binding?.emailInputs?.editableText.toString()
            val password = binding?.passwordInputs?.editableText.toString()

            if (validate(email,password)) {
                FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {

                            val firebaseUser: FirebaseUser = task.result.user!!
//                            val loggedInUser = task.toObject(Users::class.java)!!


                            activity?.let {
                                val intent = Intent(this.requireContext(), NavActivity::class.java)
                                this.startActivity(intent)
                            }
                            Toast.makeText(
                                this.requireContext(),
                                "Signed in",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        } else {
                            Toast.makeText(
                                this.requireContext(),
                                "Email or password is Empty",
                                Toast.LENGTH_SHORT
                            ).show()

                        }
                    }
                    .addOnFailureListener {
                        println(it.message)
                    }
            }
        }
        binding?.login?.setOnClickListener {
            login()
        }

    }

    fun signInSuccess(user: Users){
        activity?.let {
            val intent = Intent(this.requireContext(), NavActivity::class.java)
            this.startActivity(intent)

        }
    }
}






