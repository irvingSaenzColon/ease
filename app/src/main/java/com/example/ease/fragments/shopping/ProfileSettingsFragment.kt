package com.example.ease.fragments.shopping

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.ease.EaseApplication.Companion.session
import com.example.ease.R
import com.example.ease.activities.CarsShoppingActivity.Companion.currentSession
import com.example.ease.activities.LoginRegisterActivity
import com.example.ease.databinding.FragmentProfileSettingsBinding
import com.example.ease.model.UserModel

class ProfileSettingsFragment : Fragment(R.layout.fragment_profile_settings), View.OnClickListener {
    private var _binding : FragmentProfileSettingsBinding ? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileSettingsBinding.inflate( inflater, container, false )

        binding.cvLogout.setOnClickListener( this )
        binding.cvProfileSettings.setOnClickListener( this )
        binding.cvSecurity.setOnClickListener( this )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //TODO : Load data from preferences
        Log.i("Session sets", currentSession.id.toString())
        displayImage( currentSession )
        binding.tvUserEmail.text = currentSession.email
        binding.tvUserName.text = currentSession.nickname

    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            binding.cvLogout.id ->{
                session.clearSession()
                Intent( requireActivity(),  LoginRegisterActivity::class.java).also { intent ->
                    //Remove this activity from history so users can't go back to login
                    intent.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP )
                    startActivity( intent )
                }
            }
            binding.cvProfileSettings.id ->{
                findNavController().navigate( ProfileSettingsFragmentDirections.actionProfileFragmentToProfileOptions() )
            }
            binding.cvSecurity.id ->{
                findNavController().navigate( ProfileSettingsFragmentDirections.actionProfileFragmentToSecurityFragment() )
            }
        }
    }

    private fun displayImage( userModel : UserModel ){

        if(userModel.picture.isEmpty()) return

        val decodedString = Base64.decode( userModel.picture, Base64.DEFAULT )
        val decodedByte = BitmapFactory.decodeByteArray( decodedString, 0, decodedString.size )
        binding.ciProfileImage.setImageBitmap( decodedByte )
    }



}