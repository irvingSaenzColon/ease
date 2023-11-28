package com.example.ease.fragments.shopping

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.ease.R
import com.example.ease.adapters.MyCarsViewPagerAdapter
import com.example.ease.databinding.FragmentMyCarsBinding
import com.example.ease.util.hideBottomNavigationView
import com.google.android.material.tabs.TabLayoutMediator

class MyCarsFragment : Fragment( R.layout.fragment_my_cars ), View.OnClickListener  {
    private var _binding : FragmentMyCarsBinding ? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        hideBottomNavigationView()
        _binding = FragmentMyCarsBinding.inflate( inflater, container, false )
        binding.ibReturn.setOnClickListener( this )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val optionsFragment = arrayListOf(
            MyPublishedCarsFragment(),
            MyDraftCarsFragment()
        )

        val viewPagerAdapter2 = MyCarsViewPagerAdapter( optionsFragment, childFragmentManager, lifecycle )
        binding.vpContent.adapter = viewPagerAdapter2
        TabLayoutMediator(binding.tbOptions, binding.vpContent)  { tab, position ->
            when(position){
                0-> tab.text = "Published"
                1-> tab.text = "Drafts"
            }
        }.attach()
    }

    override fun onClick(view: View?) {
        when(view?.id){
            binding.ibReturn.id->{
                findNavController().popBackStack()
            }
        }
    }
}