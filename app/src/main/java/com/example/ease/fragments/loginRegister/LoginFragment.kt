package com.example.ease.fragments.loginRegister

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.ease.EaseApplication.Companion.session
import com.example.ease.activities.CarsShoppingActivity
import com.example.ease.databinding.FragmentLoginBinding
import com.example.ease.model.AuthenticateModel
import com.example.ease.service.APIService
import com.example.ease.util.LoginFieldsState
import com.example.ease.util.RegisterValidation
import com.example.ease.util.validateSimple
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class LoginFragment: Fragment(), View.OnClickListener {

    private var _binding: FragmentLoginBinding ? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogin.setOnClickListener( this )
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            binding.btnLogin.id -> {
                val auth = AuthenticateModel(
                    binding.etUsername.text.toString(),
                    binding.etPassword.text.toString()
                )
                if( shouldLogin( auth ) ){
                    binding.btnLogin.startAnimation()
                    signIn( auth )
                } else{
                    val validation = LoginFieldsState(
                        validateSimple(auth.credential),
                        validateSimple(auth.password)
                    )

                    if(validation.credential is RegisterValidation.Failed)
                        renderError( binding.etUsername, validation.credential.message )

                    if( validation.password is RegisterValidation.Failed )
                        renderError( binding.etPassword, validation.password.message )
                }


            }
        }
    }

    private fun signIn( authenticateModel: AuthenticateModel ){
        CoroutineScope( Dispatchers.IO ).launch {
            try{
                val response = APIService().authenticate( authenticateModel )

                if(response.body != null) session.storeSession( response.body )

                activity?.runOnUiThread {
                    Intent( requireActivity(),  CarsShoppingActivity::class.java).also { intent ->
                        //Remove this activity from history so users can't go back to login
                        intent.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP )
                        startActivity( intent )
                    }
                }

            } catch(e : Exception){
                activity?.runOnUiThread {
                    binding.btnLogin.revertAnimation()
                    Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                }
            }


        }
    }

    private fun shouldLogin( auth : AuthenticateModel ) : Boolean{
        val usernameValidate = validateSimple( auth.credential )
        val passwordValidate = validateSimple( auth.password )

        return usernameValidate is RegisterValidation.Success &&
                passwordValidate is RegisterValidation.Success
    }

    private fun renderError(editText : EditText, errorMessage: String){
        editText.apply {
            requestFocus()
            error = errorMessage
        }
    }
}