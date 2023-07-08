package com.purabmodi.devhacksapp.ui.fragments

import android.app.Activity
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.purabmodi.devhacksapp.R
import com.purabmodi.devhacksapp.data.models.Item
import com.purabmodi.devhacksapp.databinding.FragmentLostItemDetailsBinding
import com.purabmodi.devhacksapp.ml.SsdMobilenetV11Metadata1
import com.purabmodi.devhacksapp.utils.utils
import jp.wasabeef.glide.transformations.BlurTransformation
import org.tensorflow.lite.support.common.FileUtil
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import java.time.LocalDateTime

class LostItemDetailsFragment : Fragment() {
    private var _binding: FragmentLostItemDetailsBinding? = null
    private val binding get() = _binding!!
    lateinit var model: SsdMobilenetV11Metadata1
    lateinit var labels: List<String>

    val imageProcessor =
        ImageProcessor.Builder().add(ResizeOp(300, 300, ResizeOp.ResizeMethod.BILINEAR)).build()

    //    firebase objects
    private lateinit var storageRef: StorageReference
    private lateinit var database: FirebaseDatabase
    private lateinit var bitmap: Bitmap


    private lateinit var imageUri: Uri

    //        activity result
    private val startForLostImage =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data

            if (resultCode == Activity.RESULT_OK) {
                //Image Uri will not be null for RESULT_OK
                val fileUri = data?.data!!

                imageUri = fileUri

                bitmap =
                    MediaStore.Images.Media.getBitmap(requireContext().contentResolver, imageUri)
                getPredections()

                Glide.with(this)
                    .load(imageUri)
                    .apply(RequestOptions.bitmapTransform(BlurTransformation(25, 3)))
                    .into(binding.lostItemBackImage)

                binding.lostItemImageView.setImageURI(imageUri)
            } else if (resultCode == ImagePicker.RESULT_ERROR) {
                Toast.makeText(requireContext(), ImagePicker.getError(data), Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(requireContext(), "Task Cancelled", Toast.LENGTH_SHORT).show()
            }
        }

    private fun getPredections() {
        model = SsdMobilenetV11Metadata1.newInstance(requireContext())

// Creates inputs for reference.
        var image = TensorImage.fromBitmap(bitmap)
        image = imageProcessor.process(image)

// Runs model inference and gets result.
        val outputs = model.process(image)
        val locations = outputs.locationsAsTensorBuffer.floatArray
        val classes = outputs.classesAsTensorBuffer.floatArray
        val scores = outputs.scoresAsTensorBuffer.floatArray
        val numberOfDetections = outputs.numberOfDetectionsAsTensorBuffer.floatArray

        val labelList = ArrayList<String>()

        scores.forEachIndexed { index, fl ->
            var l = labels.get(classes.get(index).toInt())
            Log.d("checkingsystem", "getPredections: $l!!")
            labelList.add(l)
        }
        val nameAdapter = ArrayAdapter(requireContext(), R.layout.item_desc_layout, labelList)
        binding.lostItemName.setAdapter(nameAdapter)
        binding.lostItemCategory.setAdapter(nameAdapter)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLostItemDetailsBinding.inflate(layoutInflater, container, false)

        labels = FileUtil.loadLabels(requireContext(), "labels.txt")
        init()

        return binding.root
    }

    private fun init() {
        openCamera()
        setObjects()
        loadTodaysDate()

        binding.submitBtn.setOnClickListener {
            val name = binding.lostItemName.text.toString()
            val cat = binding.lostItemCategory.text.toString()
            val desc = binding.lostItemDesc.text.toString()
            val isVerified = false
            val submitDate = LocalDateTime.now().toString()
            val submitStudentName = "" // take from shared preferences!
            val uid = utils().getUUID()

            val loadingDialogFragment = LoadingDialogFragment()


            if (!check(name, cat)) {
                Toast.makeText(requireContext(), "Fields cant be empty", Toast.LENGTH_SHORT).show()
            } else {
                loadingDialogFragment.show(childFragmentManager, loadingDialogFragment.tag)
                storageRef.child(uid).putFile(imageUri).addOnSuccessListener { uploadTask ->
                    storageRef.child(uid).downloadUrl.addOnSuccessListener { uri ->
                        database.reference.child("items").child(uid)
                            .setValue(
                                Item(
                                    uid,
                                    name,
                                    cat,
                                    desc,
                                    isVerified,
                                    submitDate,
                                    "",
                                    submitStudentName,
                                    "",
                                    uri.toString()
                                )
                            )
                            .addOnSuccessListener {
                                Log.d("UPLOADDBCHECK", "sUCCESS")
//                                Create a pop up for successful operation!
                                loadingDialogFragment.dismiss()
                                Toast.makeText(requireContext(), "Uploaded", Toast.LENGTH_SHORT)
                                    .show()
                                findNavController().navigate(R.id.action_lostItemDetailsFragment_to_lostieFragment)
                            }
                            .addOnFailureListener {
                                Log.d("UPLOADDBCHECK", "init: $it")
                                Toast.makeText(
                                    requireContext(),
                                    "Failed to upload",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                    }
                }
            }


        }

    }

    private fun setupML() {

    }

    override fun onDestroy() {
        super.onDestroy()
        model.close()
    }

    private fun check(name: String, cat: String): Boolean {
        if (name.isBlank() && cat.isBlank())
            return false
        return true
    }

    private fun setObjects() {
        storageRef = FirebaseStorage.getInstance().reference
        database = FirebaseDatabase.getInstance()
    }

    private fun loadTodaysDate() {
        val current = LocalDateTime.now()
        val date = utils().dateFormatter(current)
        binding.lostItemDate.text = resources.getString(R.string.date, date)
    }

    private fun openCamera() {
        ImagePicker.with(this)
            .cameraOnly()
            .cropSquare()
            .compress(600)
            .createIntent { intent ->
                startForLostImage.launch(intent)
            }
    }

}