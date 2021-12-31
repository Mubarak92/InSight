package com.example.insight.fragments

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.insight.R
import com.example.insight.classes.SaveFirebase
import com.example.insight.databinding.FragmentAddBinding
import com.firebase.ui.auth.AuthUI
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.storage.FirebaseStorage
import java.util.*

class AddNewPhoto : Fragment() {
    private lateinit var filepath: Uri
    var binding: FragmentAddBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentBinding = FragmentAddBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root

        // return super.onCreateView(inflater, container, savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.choose?.setOnClickListener {
            chooser()
        }
        binding?.upload?.setOnClickListener {
            Log.e("GGG", "upload:${uploadFile()}")
            uploadFile()
            save(binding!!.titleImage.editText?.text.toString(), binding!!.overview.editText?.text.toString())
            Log.e("GGG", "upload:${uploadFile()}")

        }
    }




    private fun chooser() {
        val i = Intent(Intent.ACTION_PICK)
        i.type = "image/*"
        i.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(i, "Choose Picture"), 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && data != null) {
            filepath = data.data!!
            binding?.chosenImage?.setImageURI(data.data)
        }
    }
    private fun uploadFile() {
        context?.let {
            MaterialAlertDialogBuilder(it)
                .setTitle(resources.getString(R.string.title_add))
                .setMessage(resources.getString(R.string.support_add_page))
                .setNegativeButton(resources.getString(R.string.cancel)) { dialog, which ->
                    Toast.makeText(this.requireContext(), "Canceled", Toast.LENGTH_SHORT).show()

                }
                .setPositiveButton(resources.getString(R.string.upload)) { dialog, which ->

                        val pd = ProgressDialog(this.requireContext())
                        pd.setTitle("Uploading")
                        pd.show()
                        val currentTime: Date = Calendar.getInstance().getTime()
                        val imageRef =
                            FirebaseStorage.getInstance().reference.child("image/$currentTime.jpg")
                        imageRef.putFile(filepath).addOnSuccessListener { p0 ->
                            pd.dismiss()
                        }

                }  .show()
        }

    }

    private fun save(name: String, overview: String) {

        SaveFirebase().save(name, overview)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.signout, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.signOutMenu -> {
                AuthUI.getInstance()
                    .signOut(this.requireContext())
                findNavController().navigate(R.id.action_add2_to_loginPage)

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}