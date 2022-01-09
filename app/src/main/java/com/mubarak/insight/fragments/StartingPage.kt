package com.mubarak.insight.fragments

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat.recreate
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.firestore.FirebaseFirestore
import com.mubarak.insight.R
import com.mubarak.insight.databinding.FragmentStartPageBinding
import java.util.*

class StartingPage : Fragment() {
    private var binding: FragmentStartPageBinding? = null
    private val mFirestore = FirebaseFirestore.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



//        loadLocate() // call LoadLocate
//        setContentView(R.layout.activity_main)
//
//        val actionBar = supportActionBar
//        actionBar!!.title = resources.getString(R.string.app_name)
//
//
//binding?.changeLang?.setOnClickListener {
//    showChangeLang()
//
//
//}
//
//    }
//
//    private fun showChangeLang() {
//
//        val listItmes = arrayOf("عربي", "हिंदी", "اردو", "English")
//
//        val mBuilder = AlertDialog.Builder(this.requireContext())
//        mBuilder.setTitle("Choose Language")
//        mBuilder.setSingleChoiceItems(listItmes, -1) { dialog, which ->
//            if (which == 0) {
//                setLocate("ar")
//
//            } else if (which == 1) {
//                setLocate("hi")
//            } else if (which == 2) {
//                setLocate("ur")
//            } else if (which == 3) {
//                setLocate("en")
//            }
//
//            dialog.dismiss()
//        }
//        val mDialog = mBuilder.create()
//
//        mDialog.show()
//
//    }
//
//    private fun setLocate(Lang: String) {
//
//        val locale = Locale(Lang)
//
//        Locale.setDefault(locale)
//
//        val config = Configuration()
//
//        baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)
//
//        val editor = getSharedPreferences("Settings", Context.MODE_PRIVATE).edit()
//        editor.putString("My_Lang", Lang)
//        editor.apply()
//    }
//
//    private fun loadLocate() {
//        val sharedPreferences = getSharedPreferences("Settings", context.MODE_PRIVATE)
//        val language = sharedPreferences.getString("My_Lang", "")
//        setLocate(language)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentStartPageBinding = FragmentStartPageBinding.inflate(inflater, container, false)

        binding = fragmentStartPageBinding
        return fragmentStartPageBinding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.Login!!.setOnClickListener {
            findNavController().navigate(R.id.action_startingPage_to_loginPage)
        }

        binding?.Register?.setOnClickListener {
            findNavController().navigate(R.id.main_to_re)
        }

    }
}