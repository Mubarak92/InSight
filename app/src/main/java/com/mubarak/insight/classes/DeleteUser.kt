package com.mubarak.insight.classes

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class DeleteUser {
    fun delete() {
        val db = FirebaseFirestore.getInstance()
        Firebase.auth
        Log.e("TAG", "save: start")


        db.collection("Users").document(FirebaseAuth.getInstance().currentUser!!.uid)
            .delete()
            .addOnSuccessListener {
                // Toast.makeText(this, "Working", Toast.LENGTH_SHORT).show()
                Log.e("TAG", "save: true")
            }
            .addOnFailureListener { e ->
                Log.e("TAG", "save: error $e")

                //                Toast.makeText(this, "Fail $e", Toast.LENGTH_SHORT).show()
            }
    }
}