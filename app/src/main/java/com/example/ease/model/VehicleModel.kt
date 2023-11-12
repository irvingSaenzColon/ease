package com.example.ease.model

data class VehicleModel(
    var sells : Int = 0,
    var id : Int = 0,
    var name : String = "",
    var info : String = "",
    var price : String = "",
    var owner : Int = 0,
    var posted : String = "",
    var model : String = "",
    var username : String = "",
    var deleted : List<Int> = listOf(),
    var images : List<ImageModel> = listOf(),
    var newImages : List<ImageModel> = listOf()
)
