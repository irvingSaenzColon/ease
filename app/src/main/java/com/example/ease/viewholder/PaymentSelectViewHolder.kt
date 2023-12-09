package com.example.ease.viewholder

import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.ease.R
import com.example.ease.databinding.ItemPaymentSelectBinding
import com.example.ease.model.PaymentModel

class PaymentSelectViewHolder(view : View) : RecyclerView.ViewHolder(view) {
    private val binding = ItemPaymentSelectBinding.bind( view )
    fun render(paymentModel: PaymentModel, index : Int,onClickPayment : ( Int ) -> Unit){
        var numberSize = 0
        var lastFour  = ""

        if(paymentModel.number.length > 4){
            numberSize = paymentModel.number.length - 4
            lastFour = paymentModel.number.substring( numberSize, paymentModel.number.length )
        } else{
            lastFour = paymentModel.number
        }


        binding.tvPaymentNumber.text = lastFour
        if(paymentModel.name.length > 7){
            val nameSubs = paymentModel.name.substring(0, 6) + "..."
            binding.tvPaymentName.text = nameSubs
        } else{
            binding.tvPaymentName.text = paymentModel.name
        }

        if( paymentModel.selected ){
            binding.cvPaymentCard.setCardBackgroundColor( ContextCompat.getColor( binding.root.context, R.color.electric_indigo ) )
            binding.tvPaymentName.setTextColor( ContextCompat.getColor(binding.root.context, R.color.dr_white) )
            binding.tvPaymentNumber.setTextColor( ContextCompat.getColor(binding.root.context, R.color.dr_white) )
        } else{
            binding.cvPaymentCard.setCardBackgroundColor( ContextCompat.getColor( binding.root.context, R.color.dr_white ) )
            binding.tvPaymentName.setTextColor( ContextCompat.getColor(binding.root.context, R.color.raisin_black) )
            binding.tvPaymentNumber.setTextColor( ContextCompat.getColor(binding.root.context, R.color.raisin_black) )
        }

        binding.cvPaymentCard.setOnClickListener{ _ -> onClickPayment( index ) }
    }
}