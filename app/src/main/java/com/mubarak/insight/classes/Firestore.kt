package com.mubarak.insight.classes

import android.util.Log
import androidx.fragment.app.Fragment
import com.firebase.ui.auth.data.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.mubarak.insight.data.Users
import com.mubarak.insight.fragments.LoginPage
import com.mubarak.insight.fragments.MainPage
import com.mubarak.insight.fragments.Profile
import com.mubarak.insight.fragments.RegisterPage

class Firestore {

    private val mFirestore = FirebaseFirestore.getInstance()

    fun registerUserIntoFirestore(
        fragment: RegisterPage,
        userInfo: Users
    ) {
        mFirestore.collection("Users").document(getUserId())
            .set(userInfo, SetOptions.merge())
            .addOnCompleteListener {
                fragment.userRegistresSuccess(Users)
            }
    }

    fun signInUser(fragment: Fragment) {
        Log.e("TAG", "signInUser: userId = ${getUserId()}", )
        // Here we pass the collection name from which we wants the data.
       var a= mFirestore.collection("Users")
            .document(getUserId())

        Log.e("TAG", "signInUser: a= $a", )

            a.get()
            .addOnSuccessListener { userSnapshot ->
                Log.e("TAG", "signInUser: userSnapshot=  $userSnapshot", )
                val loggedInUser = userSnapshot.toObject(Users::class.java)!!


                when (fragment) {
                    is LoginPage -> {
                        fragment.signInSuccess(loggedInUser)
                    }
                    is Profile -> {
                        fragment.updateUserDetails(loggedInUser)
                    }
                    // END
                }
                // END
            }
            .addOnFailureListener { e ->
            }

    }

    fun getUserId(): String {
        val currentUser = FirebaseAuth.getInstance().currentUser!!

        // A variable to assign the currentUserId if it is not null or else it will be blank.
        var currentUserID = ""
        if (currentUser != null) {
            currentUserID = currentUser.uid
        }

        return currentUserID
    }
}

