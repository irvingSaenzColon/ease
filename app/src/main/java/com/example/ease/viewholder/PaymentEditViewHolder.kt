package com.example.ease.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.ease.databinding.PaymentItemBinding
import com.example.ease.model.PaymentModel

class PaymentEditViewHolder( val view : View ) : RecyclerView.ViewHolder( view ) {
    val binding = PaymentItemBinding.bind( view )
    fun render( paymentModel: PaymentModel, index : Int, onOpenPopupMenu : (View, Int, PaymentModel) -> Unit ){
        binding.tvName.text =  paymentModel.name
        binding.tvNumber.text = paymentModel.number
        binding.tvExpireDate.text = paymentModel.expire

        binding.ibPopBtn.setOnClickListener{ onOpenPopupMenu(it, index, paymentModel) }
    }
}