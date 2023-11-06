package com.example.ease.fragments.loginRegister

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.ease.EaseApplication.Companion.userRegister
import com.example.ease.R
import com.example.ease.activities.CarsShoppingActivity
import com.example.ease.databinding.FragmentRegister3Binding
import com.example.ease.helper.ApiErrorType
import com.example.ease.model.UserModel
import com.example.ease.service.APIService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.lang.Exception

class RegisterThreeFragment : Fragment(R.layout.fragment_register_3), View.OnClickListener {
    private var _binding : FragmentRegister3Binding ? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegister3Binding.inflate( inflater, container, false )

        binding.btnSignup.setOnClickListener( this )
        binding.btnBack.setOnClickListener( this )
        binding.ciProfileImage.setOnClickListener( this )

        return binding.root
    }

    override fun onClick(p0: View?) {

        when(p0?.id){
            binding.btnSignup.id -> {
                binding.btnSignup.startAnimation()
                binding.ciProfileImage.isEnabled = false
                binding.btnBack.isEnabled= false

                signUp( userRegister )
            }
            binding.ciProfileImage.id -> {
                selectImageInAlbum()
            }
            binding.btnBack.id ->{
                findNavController().navigate( RegisterThreeFragmentDirections.actionRegisterThreeFragmentToRegisterTwoFragment( error = null ) )
            }
        }
    }

    private fun signUp( userModel: UserModel ){
        CoroutineScope( Dispatchers.IO ).launch {
            try{

                val response = APIService().signup( userModel )
                Intent(requireActivity(), CarsShoppingActivity::class.java).also {intent ->
                    intent.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK )
                    startActivity( intent )
                }
            }
            catch (e : Exception){
                val apiError = ApiErrorType()
                val  error : String = e.message ?: ""
                requireActivity().runOnUiThread {
                    if(error == apiError.DUPLICATED_EMAIL)
                        findNavController().navigate( RegisterThreeFragmentDirections.actionRegisterThreeFragmentToRegisterTwoFragment( error = error ) )
                    else if (error == apiError.DUPLICATED_NICKANEM){
                        findNavController().navigate( RegisterThreeFragmentDirections.actionRegisterThreeFragmentToRegisterFragment( error = error ) )
                    }
                }
            }
        }
    }

    private val selectedImageIntent = registerForActivityResult( ActivityResultContracts.StartActivityForResult() ){
            result -> if( result.resultCode == Activity.RESULT_OK ){
                val data : Intent? = result?.data
                val pickedImage = data?.data
                val bitmap : Bitmap
                val stream = ByteArrayOutputStream()

                if( pickedImage != null && Build.VERSION.SDK_INT >= 28){
                    val source = ImageDecoder.createSource( requireContext().contentResolver, pickedImage )
                    bitmap = ImageDecoder.decodeBitmap( source )

                    bitmap.compress( Bitmap.CompressFormat.JPEG, 80, stream )
                    val byteArray = stream.toByteArray()
                    val imageBase64 = "data:image/png;base64,"+Base64.encodeToString( byteArray, Base64.DEFAULT )

                    userRegister.picture = imageBase64
                    binding.ciProfileImage.setImageBitmap( bitmap )

                } else{
                    bitmap = MediaStore.Images.Media.getBitmap(requireContext().contentResolver, pickedImage)

                    bitmap.compress( Bitmap.CompressFormat.JPEG, 80, stream )
                    val byteArray = stream.toByteArray()
                    val imageBase64 = Base64.encodeToString( byteArray, Base64.DEFAULT )
                    userRegister.picture = imageBase64

                    binding.ciProfileImage.setImageBitmap( bitmap )
                }
            }
    }

    private fun openGallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        selectedImageIntent.launch(galleryIntent)
    }

    private fun selectImageInAlbum(){
        if(ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.READ_MEDIA_IMAGES),1)
        }else {
            openGallery()
        }
    }
}