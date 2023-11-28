package com.example.ease.model.response

import com.example.ease.model.PaymentModel

data class PaymentResponse(
    val body : PaymentModel ? = null,
    val message : String = ""
)
