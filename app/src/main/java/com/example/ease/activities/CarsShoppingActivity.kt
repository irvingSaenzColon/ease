package com.example.ease.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.ease.EaseApplication.Companion.session
import com.example.ease.R
import com.example.ease.databinding.ActivityCarsShoppingBinding
import com.example.ease.model.UserModel
import com.example.ease.model.VehicleModel

class CarsShoppingActivity : AppCompatActivity() {
    companion object{
        lateinit var currentSession : UserModel
        lateinit var vehicleData : VehicleModel
    }

    private lateinit var binding  : ActivityCarsShoppingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCarsShoppingBinding.inflate( layoutInflater )
        setContentView( binding.root )

        currentSession = session.getSession()

        val navController = findNavController( R.id.shoppingHostFragment )
        binding.bottomNavigation.setupWithNavController( navController )
    }
}