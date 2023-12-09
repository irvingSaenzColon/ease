package com.example.ease.model.response

import com.example.ease.model.OrderGeneral

data class OrdersResponse(
    var body : List<OrderGeneral> = arrayListOf(),
    var message : String = ""
)
