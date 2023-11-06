package com.example.ease.fragments.loginRegister

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.RadioGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.ease.EaseApplication.Companion.userRegister
import com.example.ease.databinding.FragmentRegisterBinding
import com.example.ease.model.UserModel
import com.example.ease.util.RegisterFieldsStateOne
import com.example.ease.util.RegisterValidation
import com.example.ease.util.validateDate
import com.example.ease.util.validateName
import com.example.ease.util.validateNickname
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class RegisterFragment : Fragment(), View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    private var _binding : FragmentRegisterBinding ? = null
    private val binding get() = _binding!!

    private var gender : String = "mujer"
    private val calendar = Calendar.getInstance()

    private val args : RegisterFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentRegisterBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val error = args.error

        fillFields()

        if(error != null) errorApi( binding.etUsername, error )

        binding.etBirthdate.inputType = InputType.TYPE_NULL

        binding.btnNext.setOnClickListener( this )
        binding.etBirthdate.setOnClickListener( this )
        binding.rgGender.setOnCheckedChangeListener( this )

    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            binding.btnNext.id -> {
                val userInput = UserModel(
                    0,
                    binding.etName.text.toString(),
                    binding.etUsername.text.toString(),
                    binding.etBirthdate.text.toString(),
                    gender,
                    "",
                    "",
                    "",
                    ""
                )

                if( ! shouldContinue( userInput ) ){
                    val validation = RegisterFieldsStateOne(
                        validateName( userInput.name ),
                        validateNickname( userInput.nickname ),
                        validateDate( userInput.birthdate )
                    )

                    if(validation.name is RegisterValidation.Failed)
                        renderError( binding.etName, validation.name.message )

                    if(validation.nickname is RegisterValidation.Failed)
                        renderError( binding.etUsername, validation.nickname.message )

                    if( validation.birthdate is RegisterValidation.Failed )
                        renderError( binding.etBirthdate, validation.birthdate.message )
                }
                else{
                    userRegister.name = userInput.name
                    userRegister.nickname = userInput.nickname
                    userRegister.birthdate = userInput.birthdate
                    userRegister.gender = userInput.gender
                    findNavController().navigate( RegisterFragmentDirections.actionRegisterFragmentToRegisterTwoFragment( error = null ) )
                }

            }
            binding.etBirthdate.id ->{
                showDatePicker()
            }
        }
    }

    override fun onCheckedChanged(p0: RadioGroup?, p1: Int) {
        when(p1){
            binding.rbMale.id ->{
                gender = "hombre"
            }
            binding.rbFemale.id -> {
                gender = "mujer"
            }
            binding.rbOther.id ->{
                gender = "otro"
            }
        }
    }

    private fun showDatePicker(){
        val datePickerDialog =
            DatePickerDialog(
                requireContext(),
                { DatePicker, year :Int, month : Int, day : Int ->
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

    private fun fillFields(){
        binding.etName.setText( userRegister.name )
        binding.etUsername.setText( userRegister.nickname )
        binding.etBirthdate.setText( userRegister.birthdate )

        when(userRegister.gender){
            "hombre" -> {
                binding.rbMale.isChecked = true
            }
            "mujer" ->{
                binding.rbFemale.isChecked = true
            }
            "otro" ->{
                binding.rbOther.isChecked = true
            }
        }
    }
    private fun  renderError(editText : EditText, errorMessage : String){
        CoroutineScope( Dispatchers.IO ).launch{
            bringFeedBack( editText, errorMessage )
        }
    }

    private suspend fun bringFeedBack(editText : EditText, errorMessage : String){
        withContext( Dispatchers.IO){
           activity?.runOnUiThread{
               editText.apply {
                   requestFocus()
                   error = errorMessage
               }
           }
        }
    }

    private fun shouldContinue( userModel : UserModel ) : Boolean{
        val nameValidate = validateName( userModel.name )
        val nicknameValidate = validateNickname( userModel.nickname )
        val dateValidate = validateDate( userModel.birthdate )

        return nameValidate is RegisterValidation.Success &&
                nicknameValidate is RegisterValidation.Success &&
                dateValidate is RegisterValidation.Success
    }

    private fun errorApi(editText : EditText, errorMessage : String){
        editText.apply {
            requestFocus()
            error = errorMessage
        }
    }
}