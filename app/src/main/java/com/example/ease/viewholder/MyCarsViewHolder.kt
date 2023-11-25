package com.example.ease.viewholder

import android.graphics.BitmapFactory
import android.util.Base64
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.ease.databinding.MyCarItemBinding
import com.example.ease.model.VehicleModel

class MyCarsViewHolder(view : View) : RecyclerView.ViewHolder( view ){
    private val binding = MyCarItemBinding.bind( view )

    fun render( vehicleModel: VehicleModel, index: Int, onOpenPopUp : (View, Int, VehicleModel) -> Unit ){
        val decodedString = Base64.decode( vehicleModel.images[0].image, Base64.DEFAULT )
        val decodedByte = BitmapFactory.decodeByteArray( decodedString, 0, decodedString.size )
        binding.ivCar.setImageBitmap( decodedByte )

        binding.tvName.text = vehicleModel.name
        binding.tvModel.text = vehicleModel.model
        binding.tvPrice.text = "$ ${vehicleModel.price}"

        binding.ibOptions.setOnClickListener{ onOpenPopUp( it, index, vehicleModel ) }
    }
}