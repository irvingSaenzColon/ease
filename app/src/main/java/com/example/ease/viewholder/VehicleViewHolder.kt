package com.example.ease.viewholder

import android.graphics.BitmapFactory
import android.util.Base64
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.ease.adapters.VehicleAdapter
import com.example.ease.databinding.ProductRvItemBinding
import com.example.ease.model.VehicleModel

class VehicleViewHolder(view : View) : RecyclerView.ViewHolder(view) {
    private val binding = ProductRvItemBinding.bind( view )
    fun render(vehicleModel: VehicleModel, onClickVehicle : (VehicleModel) -> Unit){
        if(vehicleModel.images.isNotEmpty()){
            val decodedString = Base64.decode( vehicleModel.images[0].image, Base64.DEFAULT )
            val decodedByte = BitmapFactory.decodeByteArray( decodedString, 0, decodedString.size )
            binding.ivCar.setImageBitmap( decodedByte )
        }
        binding.tvName.text = vehicleModel.name
        binding.tvModel.text = vehicleModel.model
        binding.tvPrice.text = "$ ${vehicleModel.price}"

        binding.cvContainer.setOnClickListener{ onClickVehicle( vehicleModel ) }

    }
}