package com.example.ease.model

data class OrderSpecificModel(
    val id : String,
    val total : String,
    val buyAt : String,
    val returnTime : String,
    val name : String,
    val model : String,
    val image : String,
    val info : String,
    val paymentName : String,
    val paymentNumber : String
)
