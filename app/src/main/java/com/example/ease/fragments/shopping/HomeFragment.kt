package com.example.ease.fragments.shopping

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.ease.R
import com.example.ease.adapters.HomeViewpagerAdapter
import com.example.ease.databinding.FragmentHomeBinding
import com.example.ease.fragments.categories.CompactCategoryFragment
import com.example.ease.fragments.categories.DeluxCategoryFragment
import com.example.ease.fragments.categories.MainCategoryFragment
import com.example.ease.fragments.categories.SUVCategoryFragment
import com.example.ease.fragments.categories.SportCategoryFragment
import com.example.ease.fragments.categories.TruckCategoryFragment
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment : Fragment(R.layout.fragment_home) {
    private lateinit var binding : FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val categoryFragments = arrayListOf<Fragment>(
            MainCategoryFragment(),
            CompactCategoryFragment(),
            SportCategoryFragment(),
            SUVCategoryFragment(),
            TruckCategoryFragment(),
            DeluxCategoryFragment()
        )

        val viewPager2Adapter = HomeViewpagerAdapter(categoryFragments, childFragmentManager, lifecycle)
        binding.viewpagerHome.adapter = viewPager2Adapter
        TabLayoutMediator(binding.tabLayout, binding.viewpagerHome){ tab, position ->
            when(position){
                0 -> tab.text = "Main"
                1 -> tab.text = "Compact"
                2 -> tab.text= "Sport"
                3 -> tab.text = "SUV"
                4 -> tab.text = "Truck"
                5 -> tab.text = "Deluxe"
            }
        }.attach()
    }
}