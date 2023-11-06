package com.example.ease.fragments.loginRegister

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.ease.R
import com.example.ease.databinding.FragmentIntroductionBinding

class IntroductionFragment: Fragment(R.layout.fragment_introduction), View.OnClickListener {
    private var _binding : FragmentIntroductionBinding ? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentIntroductionBinding.inflate( inflater, container, false )

        binding.btnStart.setOnClickListener( this )

        return binding.root
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            binding.btnStart.id -> {
                findNavController().navigate( IntroductionFragmentDirections.actionIntroductionFragmentToAccountOptionsFragment() )
            }
        }
    }

}