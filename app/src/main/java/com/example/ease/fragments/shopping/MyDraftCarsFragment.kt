package com.example.ease.fragments.shopping

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.ease.R
import com.example.ease.databinding.FragmentMyDraftCarsBinding

class MyDraftCarsFragment : Fragment( R.layout.fragment_my_draft_cars ) {
    private var _binding : FragmentMyDraftCarsBinding ? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyDraftCarsBinding.inflate( inflater, container, false )
        return binding.root
    }
}