package com.example.ease.fragments.shopping

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.ease.R
import com.example.ease.activities.CarsShoppingActivity
import com.example.ease.activities.CarsShoppingActivity.Companion.currentSession
import com.example.ease.adapters.MyCarsAdApter
import com.example.ease.databinding.FragmentMyPublishedCarsBinding
import com.example.ease.model.VehicleModel
import com.example.ease.prefs.SessionPreference
import com.example.ease.service.APIService
import com.example.ease.util.hideBottomNavigationView
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyPublishedCarsFragment : Fragment( R.layout.fragment_my_published_cars ) {
    private var _binding : FragmentMyPublishedCarsBinding ? = null
    private val binding get() = _binding!!

    private var myCarsList = mutableListOf< VehicleModel >()
    private lateinit var myCarsAdapter : MyCarsAdApter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        hideBottomNavigationView()

        _binding = FragmentMyPublishedCarsBinding.inflate( inflater, container, false )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sessionId = currentSession.id
        Log.i("Session Id", sessionId.toString())

        getMyCars( sessionId.toString() )
    }

    private fun initRecyclerView(){
        myCarsAdapter = MyCarsAdApter( myCarsList ){ view : View, index : Int, vehicleModel : VehicleModel -> popUpMenu(view, index, vehicleModel) }
        binding.rvMyCars.adapter = myCarsAdapter
        binding.rvMyCars.layoutManager = GridLayoutManager( context, 2 )
    }

    private fun getMyCars(userId : String){
        CoroutineScope( Dispatchers.IO ).launch {
            try{
                val response = APIService().getMyVehicles( userId )
                if(response.body.isNotEmpty()) myCarsList.addAll( response.body )
                activity?.runOnUiThread {
                    initRecyclerView()
                }
            } catch ( e :Exception ){
                Log.e("API Error", e.toString())
            }
        }
    }

    private fun popUpMenu(view : View, index : Int, vehicleModel: VehicleModel){
        val popupMenu = PopupMenu( view.context.applicationContext,  view)
        popupMenu.inflate( R.menu.pop_up_menu )

        popupMenu.setOnMenuItemClickListener {menuItem ->
            when(menuItem.itemId){
                R.id.iEditQuote ->{
                    val direction = MyCarsFragmentDirections.actionMyCarsFragmentToPublishVehicleOneFragment( id = vehicleModel.id )
                    findNavController().navigate( direction )
                    true
                }
                R.id.iDeleteQuote -> {
                    deleteVehicle( vehicleModel.id, index )
                    true
                }
                else -> true
            }
        }
        popupMenu.show()
    }

    private fun deleteVehicle(id: Int, index: Int) {
        CoroutineScope( Dispatchers.IO ).launch{
            try{
                APIService().deleteVehicle( id.toString() )
                activity?.runOnUiThread {
                    myCarsList.removeAt( index )
                    myCarsAdapter.notifyDataSetChanged()
                    Toast.makeText(context, "Vehicle removed", Toast.LENGTH_SHORT).show()
                }
            } catch ( e : Exception ){
                Log.e("API Error", e.toString())
            }
        }
    }

}