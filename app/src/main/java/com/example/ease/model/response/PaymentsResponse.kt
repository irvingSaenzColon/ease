package com.example.ease.model.response

import com.example.ease.model.PaymentModel

data class PaymentsResponse(
    val body : List<PaymentModel> = arrayListOf(),
    val message : String = ""
)
