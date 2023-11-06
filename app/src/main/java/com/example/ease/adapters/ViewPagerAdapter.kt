package com.example.ease.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(fragmentManager : FragmentManager, lifeCycle : Lifecycle, val size : Int, val fragmentArray : Array<Fragment>): FragmentStateAdapter(fragmentManager, lifeCycle) {
    //Returns the amount of fragments
    override fun getItemCount(): Int {
        return size
    }

    override fun createFragment(position: Int): Fragment {
        if(position > (size - 1)){
            return  fragmentArray[0]
        }

        return fragmentArray[position]
    }

}