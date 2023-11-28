package com.example.ease.fragments.shopping

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.ease.R
import com.example.ease.adapters.VehicleAdapter
import com.example.ease.databinding.FragmentHomeBinding
import com.example.ease.model.VehicleModel
import com.example.ease.service.APIService
import com.example.ease.util.showBottomNavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeFragment : Fragment(R.layout.fragment_home) {
    private var _binding : FragmentHomeBinding ? = null
    private val binding get() = _binding!!

    private var latestVehicles = mutableListOf<VehicleModel>()
    private var sellersVehicles = mutableListOf<VehicleModel>()
    private var dealsVehicles = mutableListOf<VehicleModel>()

    private lateinit var latestAdapter : VehicleAdapter
    private lateinit var dealsAdapter : VehicleAdapter
    private lateinit var sellersAdapter : VehicleAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        showBottomNavigationView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getLatestVehicles()
        getDealsVehicles()
        getSellersVehicles()

    }

    private fun onClickVehicle(vehicleModel: VehicleModel){
        findNavController().navigate( HomeFragmentDirections.actionHomeFragmentToVehicleFragment( vehicleModel.id.toString() ) )
    }

    private fun initLatestRecyclerView(  ){
        latestAdapter = VehicleAdapter( latestVehicles ) { vehicleModel -> onClickVehicle( vehicleModel ) }
        binding.rvLatest.layoutManager = GridLayoutManager(context, 2)
        binding.rvLatest.adapter = latestAdapter
    }

    private fun initDealsRecyclerView(){
        dealsAdapter = VehicleAdapter( dealsVehicles ) { vehicleModel -> onClickVehicle( vehicleModel ) }
        binding.rvDeals.layoutManager = GridLayoutManager(context, 2)
        binding.rvDeals.adapter = dealsAdapter
    }

    private fun initSellersRecyclerView(){
        sellersAdapter = VehicleAdapter( sellersVehicles ) { vehicleModel -> onClickVehicle( vehicleModel ) }
        binding.rvSellers.layoutManager = GridLayoutManager(context, 2)
        binding.rvSellers.adapter = sellersAdapter
    }

    private fun getLatestVehicles(){
        CoroutineScope( Dispatchers.IO ).launch{
            try{
                val response = APIService().getLatestVehicles()

                if(response.body.isNotEmpty()){
                   activity?.runOnUiThread {
                       latestVehicles.clear()
                       latestVehicles.addAll( response.body )
                       initLatestRecyclerView()
                   }
                }

            } catch( e :Exception ){
                Log.e("error api", e.message ?: "")
            }
        }
    }

    private fun getDealsVehicles(){
        CoroutineScope( Dispatchers.IO ).launch{
            try{
                val response = APIService().getDealsVehicles()

                if(response.body.isNotEmpty()){
                    activity?.runOnUiThread {
                        dealsVehicles.clear()
                        dealsVehicles.addAll( response.body )
                        initDealsRecyclerView()
                    }
                }

            } catch( e :Exception ){
                Log.e("error api", e.message ?: "")
            }
        }
    }

    private fun getSellersVehicles(){
        CoroutineScope( Dispatchers.IO ).launch{
            try{
                val response = APIService().getSellersVehicles()

                if(response.body.isNotEmpty()){
                    activity?.runOnUiThread {
                        sellersVehicles.clear()
                        sellersVehicles.addAll( response.body )
                        initSellersRecyclerView()
                    }
                }

            } catch( e :Exception ){
                Log.e("error api", e.message ?: "")
            }
        }
    }
}