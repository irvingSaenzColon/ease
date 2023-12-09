package com.example.ease.fragments.shopping

import android.Manifest
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.example.ease.EaseApplication.Companion.session
import com.example.ease.R
import com.example.ease.activities.CarsShoppingActivity
import com.example.ease.activities.CarsShoppingActivity.Companion.currentSession
import com.example.ease.databinding.FragmentProfileBinding
import com.example.ease.model.UserModel
import com.example.ease.service.APIService
import com.example.ease.util.RegisterFieldsStateOne
import com.example.ease.util.RegisterValidation
import com.example.ease.util.hideBottomNavigationView
import com.example.ease.util.validateDate
import com.example.ease.util.validateName
import com.example.ease.util.validateNickname
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class ProfileFragment : Fragment(R.layout.fragment_profile), View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    private var _binding : FragmentProfileBinding ? = null
    private val binding get() = _binding!!
    private val calendar = Calendar.getInstance()

    private var selectedImage : String ?  = null
    private var selectedGender : String ? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        hideBottomNavigationView()
        _binding = FragmentProfileBinding.inflate( inflater, container, false )

        binding.btnSave.setOnClickListener( this )
        binding.ibReturn.setOnClickListener( this )
        binding.btnChangeProfileImage.setOnClickListener( this )
        binding.etBirthdate.setOnClickListener( this )
        binding.rgGender.setOnCheckedChangeListener( this )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadData( currentSession )
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            binding.ibReturn.id -> {
                findNavController().navigate( ProfileFragmentDirections.actionProfileOptionsToProfileFragment() )
            }
            binding.btnChangeProfileImage.id -> {
                selectImageInAlbum()
            }
            binding.btnSave.id ->{
                binding.btnSave.startAnimation()

                val input = getData()

                if( !shouldUpdate( input ) ){

                    val validation = RegisterFieldsStateOne(
                        name = validateName( input.name ),
                        nickname = validateNickname( input.nickname ),
                        birthdate = validateDate( input.birthdate )
                    )

                    if(validation.name is RegisterValidation.Failed)
                        renderError( binding.etName, validation.name.message )

                    if(validation.nickname is RegisterValidation.Failed)
                        renderError( binding.etUserName, validation.nickname.message )

                    if( validation.birthdate is RegisterValidation.Failed )
                        renderError( binding.etBirthdate, validation.birthdate.message )
                } else {
                    binding.btnSave.revertAnimation()
                    input.id = currentSession.id
                    update( input )

                }
            }
            binding.etBirthdate.id->{
                showDatePicker()
            }
        }
    }

    override fun onCheckedChanged(p0: RadioGroup?, p1: Int) {
        when(p1){
            binding.rbMale.id ->{
                selectedGender = "hombre"
            }
            binding.rbFemale.id -> {
                selectedGender = "mujer"
            }
            binding.rbOther.id ->{
                selectedGender = "otro"
            }
        }
    }
    private fun loadData(userModel : UserModel){
        loadImage( userModel.picture )
        binding.etName.setText( userModel.name )
        binding.etUserName.setText( userModel.nickname )
        binding.etBirthdate.setText( userModel.birthdate )
        when(userModel.gender){
            "hombre" ->{
                binding.rbMale.isChecked = true
            }
            "mujer" -> {
                binding.rbFemale.isChecked = true
            }
            "otro" -> {
                binding.rbOther.isChecked = true
            }
        }
    }
    private fun getData() : UserModel{
        if(selectedImage != null){
            Log.i("Image", "Image selected is not null")
        }
        return UserModel(
            id = currentSession.id,
            name = binding.etName.text.toString(),
            nickname = binding.etUserName.text.toString(),
            birthdate= binding.etBirthdate.text.toString(),
            gender = selectedGender ?: currentSession.gender,
            email = "",
            password = "",
            confirmPassword =  "",
            picture = selectedImage ?: ""
        )
    }

    private fun update(userModel: UserModel){
        CoroutineScope( Dispatchers.IO ).launch {
            try{
                val response = APIService().update( userModel )

                 if(response.body != null){
                     updateSession( response.body )
                     session.storeSession( currentSession )
                 }

                activity?.runOnUiThread {
                    binding.btnSave.revertAnimation()
                    Toast.makeText(context, "La información se ha editado con éxito", Toast.LENGTH_SHORT).show()
                    findNavController().popBackStack()
                }
            } catch (e : Exception){
                Log.e("Api error", e.toString())
                activity?.runOnUiThread {
                    binding.btnSave.revertAnimation()
                    Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    private fun updateSession(userModel : UserModel){
        currentSession.name = userModel.name
        currentSession.nickname = userModel.nickname
        currentSession.gender = userModel.gender
        currentSession.birthdate = userModel.gender
        currentSession.picture = userModel.picture
    }

    private fun shouldUpdate(userModel: UserModel) : Boolean {
        val nameValidation = validateName( userModel.name )
        val usernameValidation = validateNickname( userModel.nickname )
        val dateValidation = validateDate( userModel.birthdate )

        return usernameValidation is RegisterValidation.Success &&
                nameValidation is RegisterValidation.Success &&
                dateValidation is RegisterValidation.Success
    }

    private fun loadImage(baseImage : String){
        if(baseImage.isEmpty()) return

        val decodedString = Base64.decode( baseImage, Base64.DEFAULT )
        val decodedByte = BitmapFactory.decodeByteArray( decodedString, 0, decodedString.size )
        binding.ciProfileImage.setImageBitmap( decodedByte )
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

            selectedImage = imageBase64
            binding.ciProfileImage.setImageBitmap( bitmap )

        } else{
            bitmap = MediaStore.Images.Media.getBitmap(requireContext().contentResolver, pickedImage)

            bitmap.compress( Bitmap.CompressFormat.JPEG, 80, stream )
            val byteArray = stream.toByteArray()
            val imageBase64 = Base64.encodeToString( byteArray, Base64.DEFAULT )

            selectedImage = imageBase64

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

    private fun renderError(editText: EditText, errorMessage : String){
        editText.apply {
            requestFocus()
            error = errorMessage
        }
    }

    private fun showDatePicker(){
        val datePickerDialog =
            DatePickerDialog(
                requireContext(),
                { _, year :Int, month : Int, day : Int ->
                    val selectedDate = Calendar.getInstance()
                    selectedDate.set( year, month, day )

                    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault() )
                    val formattedDate = dateFormat.format( selectedDate.time )
                    binding.etBirthdate.setText( formattedDate )
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )

        datePickerDialog.show()
    }
}