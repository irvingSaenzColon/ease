package com.example.ease.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ease.R
import com.example.ease.model.PaymentModel
import com.example.ease.viewholder.PaymentSelectViewHolder

class PaymentSelectAdapter(
    private val paymentList : MutableList<PaymentModel>,
    private val onClickPayment : ( Int ) -> Unit
) : RecyclerView.Adapter<PaymentSelectViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentSelectViewHolder {
        val layoutInflater = LayoutInflater.from( parent.context )
        return PaymentSelectViewHolder( layoutInflater.inflate( R.layout.item_payment_select, parent, false ) )
    }

    override fun getItemCount(): Int = paymentList.size

    override fun onBindViewHolder(holder: PaymentSelectViewHolder, position: Int) {
        val item = paymentList[ position ]

        holder.render( item, position, onClickPayment )
    }

}