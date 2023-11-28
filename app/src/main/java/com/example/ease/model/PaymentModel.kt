package com.example.ease.model

data class PaymentModel(
    var id : String,
    var name : String,
    var number : String,
    var expire : String,
    var zip : String,
    var ownerId : Int,
    var owner : String
)
