package com.example.ease.fragments.shopping

import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.ease.R
import com.example.ease.activities.CarsShoppingActivity.Companion.currentSession
import com.example.ease.databinding.FragmentSecurityBinding
import com.example.ease.model.SecurityModel
import com.example.ease.service.APIService
import com.example.ease.util.PasswordFieldsState
import com.example.ease.util.RegisterValidation
import com.example.ease.util.validateConfirmPassword
import com.example.ease.util.validatePassword
import com.example.ease.util.validateSimple
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SecurityFragment : Fragment( R.layout.fragment_security ), View.OnClickListener {
    private var _binding : FragmentSecurityBinding ? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSecurityBinding.inflate( inflater, container, false )

        binding.btnSave.setOnClickListener( this )
        binding.ibReturn.setOnClickListener( this )
        binding.cbSeePassword.setOnCheckedChangeListener{_, isChecked ->
            if(isChecked){
                binding.etCurrentPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
                binding.etNewPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
                binding.etConfirmNewPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
            }else{
                binding.etCurrentPassword.transformationMethod = PasswordTransformationMethod.getInstance()
                binding.etNewPassword.transformationMethod = PasswordTransformationMethod.getInstance()
                binding.etConfirmNewPassword.transformationMethod = PasswordTransformationMethod.getInstance()
            }
        }

        return binding.root
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            binding.btnSave.id ->{
                disableBtn()

                val input = SecurityModel(
                    currentSession.id,
                    binding.etNewPassword.text.toString(),
                    binding.etConfirmNewPassword.text.toString(),
                    binding.etCurrentPassword.text.toString()
                    )

                if( !shouldChangePassword( input ) ){
                    val validation = PasswordFieldsState(
                        validateSimple( input.password ),
                        validatePassword( input.newPassword ),
                        validateConfirmPassword( input.newPassword, input.confirmNewPassword )
                    )
                    if(validation.currentPassword is RegisterValidation.Failed)
                        renderError( binding.etCurrentPassword, validation.currentPassword.message )

                    if(validation.newPassword is RegisterValidation.Failed)
                        renderError( binding.etNewPassword, validation.newPassword.message )

                    if( validation.confirmPassword is RegisterValidation.Failed )
                        renderError( binding.etConfirmNewPassword, validation.confirmPassword.message )

                    enableBtn()

                } else{
                    changePassword( input )
                }

            }
            binding.ibReturn.id ->{
                findNavController().navigate( SecurityFragmentDirections.actionSecurityFragmentToProfileFragment() )
            }
        }
    }

    private fun shouldChangePassword( securityModel: SecurityModel ) : Boolean{
        val cPasswordValidation = validateSimple( securityModel.password )
        val passwordValidation = validatePassword( securityModel.newPassword )
        val confirmPassword = validateConfirmPassword( securityModel.newPassword, securityModel.confirmNewPassword )

        return cPasswordValidation is RegisterValidation.Success &&
                passwordValidation is RegisterValidation.Success &&
                confirmPassword is RegisterValidation.Success
    }

    private fun changePassword( securityModel: SecurityModel ){
        CoroutineScope( Dispatchers.IO ).launch {
            try{
                APIService().changePassword( securityModel )
                activity?.runOnUiThread {
                    enableBtn()
                    clearFields()
                    Toast.makeText(context, "Password changed successfully", Toast.LENGTH_SHORT).show()
                }
            } catch( e : Exception ){
                activity?.runOnUiThread {
                    enableBtn()
                    Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun renderError(editText: EditText, errorMessage : String){
        editText.apply {
            requestFocus()
            error = errorMessage
        }
    }

    private fun clearFields(){
        binding.etCurrentPassword.setText( "" )
        binding.etNewPassword.setText( "" )
        binding.etConfirmNewPassword.setText( "" )
    }

    private fun enableBtn(){
        binding.ibReturn.isEnabled = true
        binding.btnSave.revertAnimation()
    }

    private fun disableBtn(){
        binding.ibReturn.isEnabled = false
        binding.btnSave.startAnimation()
    }

}