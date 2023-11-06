package com.example.ease.model

data class UserModel(
    val id : Int = 0,
    var name : String = "",
    var nickname : String = "",
    var birthdate : String ="",
    var gender : String = "",
    var email : String ="",
    var password : String ="",
    var confirmPassword : String = "",
    var picture : String = ""
)