package com.example.ease.fragments.shopping

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.ease.R
import com.example.ease.databinding.FragmentOrderBinding
import com.example.ease.model.OrderSpecificModel
import com.example.ease.service.APIService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OrderFragment : Fragment( R.layout.fragment_order ), View.OnClickListener {
    private lateinit var _binding : FragmentOrderBinding
    private val binding get() = _binding
    private val args : OrderFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrderBinding.inflate(inflater, container, false)

        _binding.ibReturn.setOnClickListener( this )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = args.id

        if(id != null)
            getOrder( id )

    }

    override fun onClick(view: View?) {
        when(view?.id){
            binding.ibReturn.id -> {
                findNavController().navigate( OrderFragmentDirections.actionOrderFragmentToOrdersFragment() )
            }
        }
    }

    private fun getOrder( id : String ){
        CoroutineScope( Dispatchers.IO ).launch {
            try{
                val response = APIService().getOrder( id )
                activity?.runOnUiThread {
                    fillOrderData(response.body!!)
                }
            } catch ( e: Exception ){
                Log.e("API Error", e.message ?: "")
            }
        }
    }

    private fun fillOrderData( orderSpecificModel: OrderSpecificModel ){
        val total = orderSpecificModel.total
        val rentPerDayString = "rent per day $total"
        val totalString = "$ $total"

        binding.tvPaymentName.text = orderSpecificModel.paymentName
        binding.tvPaymentNumber.text = orderSpecificModel.paymentNumber

        binding.tvCarName.text = orderSpecificModel.name
        binding.tvCarModel.text = orderSpecificModel.model
        binding.tvCarRentPerDay.text = rentPerDayString
        binding.tvCarInfo.text = orderSpecificModel.info
        binding.tvTotalPirce.text = totalString

        val decodedString = Base64.decode( orderSpecificModel.image, Base64.DEFAULT )
        val decodedByte = BitmapFactory.decodeByteArray( decodedString, 0, decodedString.size )

        binding.ivCar.setImageBitmap( decodedByte )
    }
}