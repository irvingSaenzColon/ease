package com.example.ease.fragments.shopping

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.example.ease.R
import com.example.ease.adapters.ImageAdapter
import com.example.ease.databinding.FragmentVehicleBinding
import com.example.ease.model.ImageModel
import com.example.ease.model.VehicleModel
import com.example.ease.service.APIService
import com.example.ease.util.hideBottomNavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class VehicleFragment : Fragment( R.layout.fragment_vehicle), View.OnClickListener {
    private var _binding : FragmentVehicleBinding ? = null
    private val binding get() = _binding!!

    private val args : VehicleFragmentArgs by navArgs()
    private lateinit var vehicleData : VehicleModel
    private lateinit var imagesAdapter : ImageAdapter
    private var dotsIndicators : MutableList<ImageView> = mutableListOf()
    private val linearLayoutParams = LinearLayout.LayoutParams(
                                        LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.WRAP_CONTENT
                                    ).apply { setMargins(8, 0, 8, 0) }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        hideBottomNavigationView()
        _binding = FragmentVehicleBinding.inflate( inflater, container, false )

        binding.ibReturn.setOnClickListener( this )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = args.id

        if( id != null ) getVehicleData( id )
    }

    override fun onClick(view: View?) {
        when(view?.id){
            binding.ibReturn.id->{
                findNavController().popBackStack()
            }
        }
    }

    private fun getVehicleData(id : String){
        CoroutineScope( Dispatchers.IO ).launch {
            try{
                val response = APIService().getVehicle( id )

                if(response.body != null) vehicleData = response.body
                activity?.runOnUiThread {
                    if( vehicleData != null )
                        fillData( vehicleData )
                }
            } catch (e : Exception){
                Log.i("Error API", e.message ?: "")
            }
        }
    }

    private fun fillData(vehicleModel: VehicleModel){
        binding.tvName.text = vehicleModel.name
        binding.tvModel.text = vehicleModel.model
        binding.tvPrice.text = "$ ${vehicleModel.price}"
        binding.tvDesc.text = vehicleModel.info

        initViewPager( vehicleModel.images )
        initDotIndicators( vehicleModel.images )
    }

    private fun initViewPager(imageList : List<ImageModel>){
        imagesAdapter = ImageAdapter( imageList )
        binding.vpPreviews.adapter = imagesAdapter
        binding.vpPreviews.registerOnPageChangeCallback( object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                dotsIndicators.mapIndexed { index, imageView ->
                    if(position == index)
                        imageView.setImageResource( R.drawable.active_dot )
                    else
                        imageView.setImageResource( R.drawable.unactive_dot )
                }
            }
        } )
    }
    private fun initDotIndicators(imageList: List<ImageModel>){

        dotsIndicators = MutableList(imageList.size){ ImageView( context ) }
        dotsIndicators.forEach{
            it.setImageResource( R.drawable.unactive_dot )
            binding.llDotsIndicators.addView( it, linearLayoutParams )
        }
        dotsIndicators[0].setImageResource( R.drawable.active_dot )
    }
}