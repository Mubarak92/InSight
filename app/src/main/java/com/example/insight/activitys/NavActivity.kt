package com.example.insight.activitys

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.insight.R
import com.example.insight.databinding.ActivityMainBinding
import com.example.insight.databinding.ActivityNavBinding
import com.example.insight.fragments.AddNewPhoto
import com.example.insight.fragments.MainPage
import com.example.insight.fragments.Profile
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth

class NavActivity : AppCompatActivity() {
    lateinit var currentfragment: androidx.fragment.app.Fragment
    private lateinit var mAuth: FirebaseAuth
    private lateinit var navController: NavController

    //    private lateinit var navController: NavController
    lateinit var binding: ActivityNavBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNavBinding.inflate(layoutInflater)



        mAuth = FirebaseAuth.getInstance()
        val user = mAuth.currentUser

        if (user != null) {
            val mainPage = Intent(this, MainPage::class.java)
            startActivity(mainPage)
        }



        setContentView(binding.root)
        supportFragmentManager.beginTransaction().replace(R.id.nav_container, MainPage()).commit()
        val bottomNav: BottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNav.setOnNavigationItemSelectedListener(navListener)
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        bottomNavigationView.setupWithNavController(navController)
    }


    val navListener = BottomNavigationView.OnNavigationItemSelectedListener {
        when (it.itemId) {
            R.id.home -> {
                currentfragment = MainPage()
            }
            R.id.add -> {
                currentfragment = Profile()
            }
            R.id.profile -> {
                currentfragment = AddNewPhoto()
            }
        }
        supportFragmentManager.beginTransaction().replace(R.id.nav_container, currentfragment)
            .commit()
        true
    }
}


//val navHostFragment = supportFragmentManager
//    .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
//
//navController = navHostFragment.navController
//setupActionBarWithNavController(navController)


//
//        makeCurrentFragment(homeFragment)
//
//////        bottomNav.set
//
//////        supportFragmentManager.beginTransaction().replace(R.id.wrapper).commit()
//        BottomNavigationView.OnNavigationItemSelectedListener { item ->
//            when (item.itemId) {
//                R.id.item1 -> {
//                    makeCurrentFragment(homeFragment)
//                    true
//                }
//                R.id.item2 -> {
//                    makeCurrentFragment(addFragment)
//                    true
//                }
//                else -> false
//            }
//        }
//    }
//
//    private fun makeCurrentFragment(fragment: Fragment) =
//        supportFragmentManager.beginTransaction().apply { replace(R.id.wrapper, fragment).commit() }
//}
//