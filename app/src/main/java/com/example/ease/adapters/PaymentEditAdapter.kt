package com.example.ease.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.example.ease.R
import com.example.ease.model.PaymentModel
import com.example.ease.viewholder.PaymentEditViewHolder

class PaymentEditAdapter(
    private val paymentList : MutableList< PaymentModel >,
    private val onOpenPopupMenu: (View, Int, PaymentModel) -> Unit
) : RecyclerView.Adapter<PaymentEditViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentEditViewHolder {
        val layoutInflater = LayoutInflater.from( parent.context )

        return PaymentEditViewHolder( layoutInflater.inflate( R.layout.payment_item, parent, false ) )
    }

    override fun getItemCount(): Int = paymentList.size

    override fun onBindViewHolder(holder: PaymentEditViewHolder, position: Int) {
        val item = paymentList[ position ]

        holder.render( item, position,  onOpenPopupMenu)
    }
}