package com.example.ease.fragments.shopping

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ease.R
import com.example.ease.adapters.CategoryAdapter
import com.example.ease.adapters.VehicleAdapter
import com.example.ease.databinding.FragmentSearchBinding
import com.example.ease.model.CategoryModel
import com.example.ease.model.SearchModel
import com.example.ease.model.VehicleModel
import com.example.ease.service.APIService
import com.example.ease.util.showBottomNavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchFragment : Fragment(R.layout.fragment_search), SearchView.OnQueryTextListener {
    private var _binding : FragmentSearchBinding ? = null
    private val binding get() =  _binding!!
    private val searchModel = SearchModel()
    private var vehicleList = mutableListOf<VehicleModel>()
    private lateinit var vehicleAdapter : VehicleAdapter

    private lateinit var categoryAdapter : CategoryAdapter
    private var categoryList  = mutableListOf<CategoryModel>()
    private var categoryIndexSelected : Int = -1
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate( inflater, container, false )

        categoryIndexSelected = 0
        categoryList.add( CategoryModel(-1, "Todos", true) )
        initRecyclerViewVehicles()
        initRecyclerViewCategories()
        binding.swSearch.setOnQueryTextListener( this )

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        showBottomNavigationView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getCategories()
        searchVehicles()
    }

    private fun initRecyclerViewCategories(){
        categoryAdapter = CategoryAdapter( categoryList) { index -> onSelectCategory( index ) }
        binding.rvCategories.adapter = categoryAdapter
        binding.rvCategories.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun initRecyclerViewVehicles(){
        vehicleAdapter = VehicleAdapter( vehicleList ) { vehicleModel -> onClickVehicle( vehicleModel ) }
        binding.rvResults.adapter = vehicleAdapter
        binding.rvResults.layoutManager = GridLayoutManager(context, 2)
    }

    private fun onClickVehicle(vehicleModel: VehicleModel){
     //   findNavController().navigate( HomeFragmentDirections.actionHomeFragmentToVehicleFragment( vehicleModel.id.toString() ) )
    }

    private fun onSelectCategory(index: Int){
        if(categoryIndexSelected != -1){
            categoryList[ categoryIndexSelected ].selected = false
        }
        categoryList[ index ].selected = true
        categoryIndexSelected = index

        categoryAdapter.notifyDataSetChanged()

        if(categoryList[ categoryIndexSelected ].name == "Todos") searchModel.category = ""
        else searchModel.category = categoryList[ categoryIndexSelected ].name

        searchVehicles()
    }

    private fun getCategories(){
        CoroutineScope( Dispatchers.IO ).launch {
            try{
                val response = APIService().getCategories()
                if(response.body.isNotEmpty()){
                    activity?.runOnUiThread {
                        categoryList.addAll( response.body )
                        categoryAdapter.notifyItemRangeInserted(1, response.body.size)
                    }
                }
            } catch ( e : Exception){
                Log.e("API Error", e.toString())
            }
        }
    }

    private fun searchVehicles(){
        CoroutineScope( Dispatchers.IO ).launch {
            try{
                val response = APIService().searchVehicles( searchModel )

                activity?.runOnUiThread {
                    vehicleList.clear()
                    if(response.body.isNotEmpty()){
                        vehicleList.addAll( 0, response.body )
                    }
                    vehicleAdapter.notifyDataSetChanged()
                }

            } catch ( e : Exception){
                Log.e("Error API", e.toString())
            }
        }
    }

    override fun onQueryTextSubmit(query: String): Boolean {
        binding.swSearch.clearFocus()
        searchModel.name = query
        Log.i("Search", searchModel.name)
        searchVehicles()
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return false
    }


}