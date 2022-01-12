package com.mubarak.insight.activitys

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mubarak.insight.R
import com.mubarak.insight.databinding.ActivityNavBinding

class NavActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    lateinit var binding: ActivityNavBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNavBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_container) as NavHostFragment
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation_buttons)
        navController = navHostFragment.navController

        bottomNavigationView.setupWithNavController(navController)
    }


}

