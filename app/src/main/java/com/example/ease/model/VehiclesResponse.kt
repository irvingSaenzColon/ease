package com.example.ease.model

data class VehiclesResponse(
    val body: List<VehicleModel> = arrayListOf(),
    val  message : String = ""
)