package com.example.ease.model

data class SecurityModel(
    val id : Int,
    val newPassword : String,
    val confirmNewPassword : String,
    val password: String
)
