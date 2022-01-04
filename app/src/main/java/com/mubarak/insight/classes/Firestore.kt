package com.mubarak.insight.classes

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.mubarak.insight.data.Users
import com.mubarak.insight.fragments.RegisterPage

class Firestore {

    private val mFirestore = FirebaseFirestore.getInstance()

    fun registerUserIntoFirestore(
        fragment: RegisterPage,
        userInfo: Users
    ){
        mFirestore.collection("Users").document(getUserId()).set(userInfo, SetOptions.merge())
            .addOnSuccessListener {
                fragment.userRegistresSuccess()
            }
    }

    fun getUserId(): String {

        val currentUser = FirebaseAuth.getInstance().currentUser
        var currentUserID = ""
        if (currentUser != null) {

            currentUserID = currentUser.uid
        }

        return currentUserID


    }

    }