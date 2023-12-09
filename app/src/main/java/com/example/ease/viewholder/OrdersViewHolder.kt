package com.example.ease.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.ease.databinding.ItemOrderBinding
import com.example.ease.model.OrderGeneral

class OrdersViewHolder(view : View) : RecyclerView.ViewHolder( view ) {
    val binding = ItemOrderBinding.bind( view )
    fun render( orderGeneral : OrderGeneral, index : String, onOrderClick : (String) -> Unit){
        binding.tvOrder.text = "Order #$index"
        binding.tvDate.text = orderGeneral.buyAt
        binding.cardContainer.setOnClickListener{ _ -> onOrderClick( orderGeneral.id ) }
    }

}