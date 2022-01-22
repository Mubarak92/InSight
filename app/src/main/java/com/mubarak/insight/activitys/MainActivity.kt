package com.mubarak.insight.activitys

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.mubarak.insight.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    //=================================================================================
// binding has been declared and navController
    private lateinit var navController: NavController
    lateinit var binding: ActivityMainBinding
    //=================================================================================

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //=================================================================================
//        val toolbar = findViewById<Toolbar>(com.mubarak.insight.R.id.toolbar)
//        setSupportActionBar(toolbar)


        // navigation has been declared here
        val navHostFragment = supportFragmentManager
            .findFragmentById(com.mubarak.insight.R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        setupActionBarWithNavController(navController)

    }

    //=================================================================================
// onSupportNavigateUp() is used to add back arrow
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}