package com.example.ease.model.response

import com.example.ease.model.OrderSpecificModel

data class OrderResponse(
    var body : OrderSpecificModel? = null,
    var message : String = ""
)
