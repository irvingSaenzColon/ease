package com.example.ease.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ease.R
import com.example.ease.model.OrderGeneral
import com.example.ease.viewholder.OrdersViewHolder

class OrdersAdapter(
    private val orderList : List< OrderGeneral >,
    private val onClickOrder : (String) -> Unit
) : RecyclerView.Adapter<OrdersViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrdersViewHolder {
        val layoutInflater = LayoutInflater.from( parent.context )

        return OrdersViewHolder( layoutInflater.inflate( R.layout.item_order, parent, false ) )
    }

    override fun getItemCount(): Int = orderList.size

    override fun onBindViewHolder(holder: OrdersViewHolder, position: Int) {
        val item = orderList[ position ]
        val orderNumber = position + 1

        holder.render( item, orderNumber.toString(), onClickOrder )
    }
}