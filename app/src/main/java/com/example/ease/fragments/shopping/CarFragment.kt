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
import com.example.ease.databinding.FragmentCarBinding
import com.example.ease.model.VehicleModel
import com.example.ease.service.APIService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CarFragment : Fragment(R.layout.fragment_car), View.OnClickListener{
    private var _binding : FragmentCarBinding ? = null
    private val binding get() = _binding!!
    //RecyclerView
    private lateinit var vehicleAdapter : VehicleAdapter
    private val vehicleList = mutableListOf<VehicleModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCarBinding.inflate( inflater, container, false )

        binding.btnAddVehicle.setOnClickListener( this )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        getVehicles()
    }

    override fun onClick(view: View?) {
        when( view?.id ){
            binding.btnAddVehicle.id->{
                findNavController().navigate( CarFragmentDirections.actionCartFragmentToPublishVehicleOneFragment( id = -1 ) )
            }
        }
    }

    private fun onClickVehicle(vehicleModel: VehicleModel){
        findNavController().navigate( CarFragmentDirections.actionCarFragmentToVehicleFragment( vehicleModel.id.toString() ) )
    }

    private fun initRecyclerView(){
        vehicleAdapter = VehicleAdapter( vehicleList ) { vehicleModel -> onClickVehicle(vehicleModel ) }
        binding.rvPublishedCars.layoutManager = GridLayoutManager(context, 2)
        binding.rvPublishedCars.adapter = vehicleAdapter
    }

    private fun getVehicles(){
        CoroutineScope( Dispatchers.IO ).launch {
            try{
                val response = APIService().getVehicles()
                activity?.runOnUiThread {
                    vehicleList.clear()
                    vehicleList.addAll(response.body)
                    initRecyclerView()
                }
            } catch( e : Exception ){
                Log.e("Get error", e.toString())
            }
        }
    }
}