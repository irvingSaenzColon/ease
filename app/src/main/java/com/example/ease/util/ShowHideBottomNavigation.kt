package com.example.ease.util

import android.view.View
import androidx.fragment.app.Fragment
import com.example.ease.R
import com.example.ease.activities.CarsShoppingActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

fun Fragment.hideBottomNavigationView(){
    val bottomNavigation = (activity as CarsShoppingActivity).findViewById<BottomNavigationView>(R.id.bottomNavigation)
    bottomNavigation.visibility = View.GONE
}

fun Fragment.showBottomNavigationView(){
    val bottomNavigation = (activity as CarsShoppingActivity).findViewById<BottomNavigationView>(R.id.bottomNavigation)
    bottomNavigation.visibility = View.VISIBLE
}