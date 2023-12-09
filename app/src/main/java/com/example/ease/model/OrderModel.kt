package com.example.ease.model

data class OrderModel (
    val orderId : Int,
    val userId : Int,
    val carId : Int,
    val paymethodId : Int,
    val total : Float,
    val returnDate : String
)