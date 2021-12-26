package com.example.insight

import android.util.Log
import com.google.firebase.auth.ktx.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*
import kotlin.collections.HashMap

class SaveFirebase {
    fun save(name: String, overview: String) {
        val db = Firebase.firestore
        Firebase.auth
        Log.e("TAG", "save: start")


        val person: MutableMap<String, String> = HashMap()
        person["name"] = name
        person["city"] = overview

            db.collection("person")
            .add(person)
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