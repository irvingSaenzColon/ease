package com.example.ease.fragments.loginRegister

import android.R.attr.password
import android.os.Bundle
import android.text.InputType
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.ease.EaseApplication.Companion.userRegister
import com.example.ease.R
import com.example.ease.databinding.FragmentRegister2Binding
import com.example.ease.model.UserModel
import com.example.ease.util.RegisterFieldsStateTwo
import com.example.ease.util.RegisterValidation
import com.example.ease.util.validateConfirmPassword
import com.example.ease.util.validateEmail
import com.example.ease.util.validatePassword
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class RegisterTwoFragment : Fragment(R.layout.fragment_register_2), View.OnClickListener {
    private var _binding : FragmentRegister2Binding ? = null
    private val binding get() = _binding!!

    private val args : RegisterFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegister2Binding.inflate( inflater, container, false )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val error = args.error

        fillFields()

        if(error != null) errorApi( binding.etEmail, error )

        binding.btnNext.setOnClickListener( this )
        binding.btnPrevious.setOnClickListener( this )
        binding.cbSeePassword.setOnCheckedChangeListener{_, isChecked ->
            if(isChecked){
                binding.etPasswordRegister.transformationMethod = HideReturnsTransformationMethod.getInstance()
                binding.etConfirmPasswordRegister.transformationMethod = HideReturnsTransformationMethod.getInstance()
            }else{
                binding.etPasswordRegister.transformationMethod = PasswordTransformationMethod.getInstance()
                binding.etConfirmPasswordRegister.transformationMethod = PasswordTransformationMethod.getInstance()
            }
        }

    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            binding.btnNext.id -> {
                val userInput = UserModel(0, "", "","", "",
                    binding.etEmail.text.toString(),
                    binding.etPasswordRegister.text.toString(),
                    binding.etConfirmPasswordRegister.text.toString(), "" )
                if(!shouldContinue( userInput )){
                    val validation = RegisterFieldsStateTwo(
                        validateEmail( userInput.email ),
                        validatePassword( userInput.password ),
                        validateConfirmPassword( userInput.password, userInput.confirmPassword )
                    )

                    if(validation.email is RegisterValidation.Failed)
                        renderError( binding.etEmail, validation.email.message )

                    if(validation.password is RegisterValidation.Failed)
                        renderError( binding.etPasswordRegister, validation.password.message )

                    if( validation.confirmPassword is RegisterValidation.Failed )
                        renderError( binding.etConfirmPasswordRegister, validation.confirmPassword.message )

                }
                else{
                    userRegister.email = userInput.email
                    userRegister.password = userInput.password
                    userRegister.confirmPassword = userInput.confirmPassword
                    findNavController().navigate(  RegisterTwoFragmentDirections.actionRegisterTwoFragmentToRegisterThreeFragment())
                }

            }
            binding.btnPrevious.id -> {
                findNavController().navigate( RegisterTwoFragmentDirections.actionRegisterTwoFragmentToRegisterFragment( error = null ) )
            }
        }
    }

    private fun fillFields(){
        binding.etEmail.setText( userRegister.email )
        binding.etPasswordRegister.setText( userRegister.password )
        binding.etConfirmPasswordRegister.setText(( userRegister.confirmPassword ))
    }

    private fun shouldContinue( userModel : UserModel) : Boolean{
        val emailValidate = validateEmail( userModel.email )
        val passwordValidate = validatePassword( userModel.password )
        val confirmPasswordValidate = validateConfirmPassword( userModel.password, userModel.confirmPassword )

        return emailValidate is RegisterValidation.Success &&
                passwordValidate is RegisterValidation.Success &&
                confirmPasswordValidate is RegisterValidation.Success
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

    private fun errorApi(editText: EditText, errorMessage: String){
        editText.apply {
            requestFocus()
            error = errorMessage
        }
    }
}