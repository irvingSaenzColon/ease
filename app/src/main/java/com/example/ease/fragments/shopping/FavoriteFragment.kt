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
import com.example.ease.activities.CarsShoppingActivity.Companion.currentSession
import com.example.ease.adapters.VehicleAdapter
import com.example.ease.databinding.FragmentFavoriteBinding
import com.example.ease.model.VehicleModel
import com.example.ease.service.APIService
import com.example.ease.util.hideBottomNavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteFragment : Fragment( R.layout.fragment_favorite ), View.OnClickListener {
    private lateinit var _binding :  FragmentFavoriteBinding
    private val binding get() = _binding
    private var vehicleList = mutableListOf<VehicleModel>()
    private lateinit var vehicleAdapter : VehicleAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate( inflater, container, false )

        _binding.btnReturn.setOnClickListener( this )
        initRecyclerView()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hideBottomNavigationView()

        val id = currentSession.id

        loadFavorites( id.toString() )
    }

    override fun onClick(view: View?) {
        when(view?.id){
            binding.btnReturn.id -> {
                findNavController().popBackStack()
            }
        }
    }

    private fun loadFavorites( id : String ){
        CoroutineScope( Dispatchers.IO ).launch {
            try{
                val response = APIService().getFavoritesFrom( id )
                Log.i("API Response", response.body.size.toString())
                activity?.runOnUiThread {
                    vehicleList.clear()
                    vehicleList.addAll(0, response.body )
                    vehicleAdapter.notifyDataSetChanged()

                }
            } catch (e : Exception){
                Log.e("API Error", e.message ?: "")
            }
        }
    }

    private fun initRecyclerView(){
        vehicleAdapter = VehicleAdapter( vehicleList ) { vehicleModel -> onClickVehicle( vehicleModel ) }
        binding.rvFavorites.adapter = vehicleAdapter
        binding.rvFavorites.layoutManager = GridLayoutManager(context, 2)
    }

    private fun onClickVehicle(vehicleModel: VehicleModel){
        findNavController().navigate( FavoriteFragmentDirections.actionFavoriteFragmentToVehicleFragment( vehicleModel.id.toString() ) )
    }
}