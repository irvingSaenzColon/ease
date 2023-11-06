package com.example.ease.fragments.loginRegister

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.ease.R
import com.example.ease.databinding.FragmentAccountOptionsBinding

class AccountOptionsFragment: Fragment(R.layout.fragment_account_options), View.OnClickListener {
    private var _binding : FragmentAccountOptionsBinding ? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAccountOptionsBinding.inflate( inflater, container, false )

        binding.btnGoToLogin.setOnClickListener( this )
        binding.btnGoToRegister.setOnClickListener( this )

        return binding.root
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            binding.btnGoToLogin.id -> {
                findNavController().navigate( AccountOptionsFragmentDirections.actionAccountOptionsFragmentToLoginFragment() )
            }
            binding.btnGoToRegister.id -> {
                findNavController().navigate( AccountOptionsFragmentDirections.actionAccountOptionsFragmentToRegisterFragment( error = null ) )
            }
        }
    }
}