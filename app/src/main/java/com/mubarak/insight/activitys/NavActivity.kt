package com.mubarak.insight.activitys

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController

import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.mubarak.insight.R
import com.mubarak.insight.databinding.ActivityNavBinding

class NavActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    //    private lateinit var navController: NavController
    lateinit var binding: ActivityNavBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNavBinding.inflate(layoutInflater)



//        mAuth = FirebaseAuth.getInstance()
//        val user = mAuth.currentUser

//        if (user != null) {
//            val mainPage = Intent(this, MainPage::class.java)
//            startActivity(mainPage)
//        }

        setContentView(binding.root)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_container) as NavHostFragment
//        supportFragmentManager.beginTransaction().replace(R.id.nav_container, MainPage()).commit()
//        val bottomNav: BottomNavigationView = findViewById(R.id.bottom_navigation)
//        bottomNav.setOnNavigationItemSelectedListener(navListener)
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation_buttons)
        navController = navHostFragment.navController

        bottomNavigationView.setupWithNavController(navController)
    }}


//    val navListener = BottomNavigationView.OnNavigationItemSelectedListener {
//        when (it.itemId) {
//            R.id.home -> {
//                currentfragment = MainPage()
//            }
//            R.id.add -> {
//                currentfragment = Profile()
//            }
//            R.id.profile -> {
//                currentfragment = AddNewPhoto()
//            }
//        }
//        supportFragmentManager.beginTransaction().replace(R.id.nav_container, currentfragment)
//            .commit()
//        true
//
//
