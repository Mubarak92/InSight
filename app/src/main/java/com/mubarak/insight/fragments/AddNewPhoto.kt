package com.mubarak.insight.fragments

import android.Manifest
import android.app.Activity
import android.app.ProgressDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import com.firebase.ui.auth.AuthUI
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.mubarak.insight.R
import com.mubarak.insight.databinding.FragmentAddBinding
import com.mubarak.insight.viewmodel.SaveFirebase
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_camera.*
import kotlinx.android.synthetic.main.fragment_overview.*
import kotlinx.android.synthetic.main.fragment_start_page.*
import java.io.File
import java.nio.ByteBuffer
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

private const val PICK_PHOTO_CODE = 1234
val REQUEST_IMAGE_CAPTURE = 1

class AddNewPhoto : Fragment() {
    private var filePath: Uri? = null
    private val mFirestore = FirebaseFirestore.getInstance()
    private var storageReference: StorageReference? = null
    var binding: FragmentAddBinding? = null
    private var imageCapture: ImageCapture? = null
    lateinit var currentPhotoPath: String

    private lateinit var outputDirectory: File
    private lateinit var cameraExecutor: ExecutorService

//    private lateinit var images:MutableList<Images>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        storageReference = FirebaseStorage.getInstance().reference
        if (allPermissionsGranted()) {
            startCamera()
        } else {
            ActivityCompat.requestPermissions(
                this.requireActivity(), Camera.REQUIRED_PERMISSIONS, Camera.REQUEST_CODE_PERMISSIONS
            )
        }

        // Set up the listener for take photo button
        binding?.camera?.setOnClickListener { startCamera() }

        outputDirectory = getOutputDirectory()

        cameraExecutor = Executors.newSingleThreadExecutor()
    }

    val REQUEST_IMAGE_CAPTURE = 1

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
            uploadFile()
        }
        binding?.camera?.setOnClickListener {
            takePhoto()
        }

    }

    private fun takePhoto() {
        // Get a stable reference of the modifiable image capture use case
        val imageCapture = imageCapture ?: return
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        } catch (e: ActivityNotFoundException) {
            // display error state to the user
        }
        // Create time-stamped output file to hold the image
        val photoFile = File(
            outputDirectory,
            SimpleDateFormat(
                FILENAME_FORMAT, Locale.US
            ).format(System.currentTimeMillis()) + ".jpg"
        ).apply { currentPhotoPath = absolutePath }

        // Create output options object which contains file + metadata
        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        // Set up image capture listener, which is triggered after photo has
        // been taken
        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(this.requireContext()),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    Log.e(TAG, "Photo capture failed: ${exc.message}", exc)
                }

                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE).also { mediaScanIntent ->
                        val f = File(currentPhotoPath)
                        mediaScanIntent.data = Uri.fromFile(f)
                        activity?.sendBroadcast(mediaScanIntent)
                    }
                    val msg = "Photo capture succeeded"
                    Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
                    Log.d(TAG, msg)
                }
            })
    }


    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this.requireContext())

        cameraProviderFuture.addListener(Runnable {
            // Used to bind the lifecycle of cameras to the lifecycle owner
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

//            // Preview
//            val preview = Preview.Builder()
//                .build()
//                .also {
//                    it.setSurfaceProvider(viewFinder.surfaceProvider)
//                }

            imageCapture = ImageCapture.Builder()
                .build()

            val imageAnalyzer = ImageAnalysis.Builder()
                .build()
                .also {
                    it.setAnalyzer(cameraExecutor, Camera.LuminosityAnalyzer { luma ->
                        Log.d(TAG, "Average luminosity: $luma")
                    })
                }

            // Select back camera as a default
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                // Unbind use cases before rebinding
                cameraProvider.unbindAll()

                // Bind use cases to camera
                cameraProvider.bindToLifecycle(
                    this, cameraSelector, imageCapture, imageAnalyzer
                )

            } catch (exc: Exception) {
                Log.e(TAG, "Use case binding failed", exc)
            }

        }, ContextCompat.getMainExecutor(this.requireContext()))
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            requireActivity().baseContext, it
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun getOutputDirectory(): File {
        val mediaDir = activity?.externalMediaDirs?.firstOrNull()?.let {
            File(
                it,
                resources.getString(androidx.camera.core.R.string.androidx_camera_default_config_provider)
            ).apply { mkdirs() }
        }
        return if (mediaDir != null && mediaDir.exists())
            mediaDir else requireActivity().filesDir
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }

    companion object {
        private const val TAG = "CameraXBasic"
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults:
        IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                startCamera()
            } else {
                Toast.makeText(
                    this.requireContext(),
                    "Permissions not granted by the user.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    class LuminosityAnalyzer(private val listener: LumaListener) : ImageAnalysis.Analyzer {

        private fun ByteBuffer.toByteArray(): ByteArray {
            rewind()    // Rewind the buffer to zero
            val data = ByteArray(remaining())
            get(data)   // Copy the buffer into a byte array
            return data // Return the byte array
        }

        override fun analyze(image: ImageProxy) {

            val buffer = image.planes[0].buffer
            val data = buffer.toByteArray()
            val pixels = data.map { it.toInt() and 0xFF }
            val luma = pixels.average()

            listener(luma)

            image.close()
        }
    }


    private fun chooser() {
        val i = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        i.type = "image/*"
        i.action = Intent.ACTION_GET_CONTENT
        if (i != null) {
            startActivityForResult(i, PICK_PHOTO_CODE)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && data != null) {

//            filePath = data.data!!
//            binding?.ivImage?.setImageURI(filePath)
//

            val imageBitmap = data.extras?.get("data") as Bitmap
            imageView.setImageBitmap(imageBitmap)

        } else {
            Toast.makeText(this.requireContext(), "Image pick canceled", Toast.LENGTH_SHORT).show()
        }
    }

    private fun save(
        uri: String,
        systemTime: Long = System.currentTimeMillis(),
        title: String,
        overview: String
    ) {

        SaveFirebase().save(uri, systemTime, title, overview)
    }


    private fun uploadFile() {

        val pd = ProgressDialog(this.requireContext())
        pd.setTitle("Uploading")
        pd.show()
        if (filePath != null) {

            val imageRef =
                FirebaseStorage.getInstance().reference.child("images/${System.currentTimeMillis()}-Photo.jpg\"")
            val uploadTask = imageRef.putFile(filePath!!)

            val urlTask =
                uploadTask.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
                    if (!task.isSuccessful) {
                        task.exception?.let {
                            throw it
                        }
                    }
                    return@Continuation imageRef.downloadUrl
                })?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {

                        pd.dismiss()
                        Toast.makeText(
                            this.requireContext(),
                            "Photo uploaded successfully",
                            Toast.LENGTH_SHORT
                        ).show()
                        val downloadUri = task.result
                        save(
                            downloadUri.toString(),
                            System.currentTimeMillis(),
                            title_image_input.text.toString(),
                            overview_input.text.toString(),
//                            link1_input.text.toString().toUri(),
//                            link2_input.text.toString().toUri()

                        )

                        Log.e("TAG", "massage:$filePath")
                    } else {
                        // Handle failures
                    }
                }?.addOnFailureListener {

                }
        }

    }
}