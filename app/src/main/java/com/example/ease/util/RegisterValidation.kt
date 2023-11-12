package com.example.ease.util

sealed class RegisterValidation(){
    object Success : RegisterValidation()
    data class Failed(val message: String) : RegisterValidation()
}


data class RegisterFieldsStateOne(
    val name : RegisterValidation,
    val nickname : RegisterValidation,
    val birthdate : RegisterValidation
)

data class RegisterFieldsStateTwo(
    val email : RegisterValidation,
    val password : RegisterValidation,
    val confirmPassword : RegisterValidation
)

data class LoginFieldsState(
    val credential : RegisterValidation,
    val password : RegisterValidation
)

data class PasswordFieldsState(
    val currentPassword : RegisterValidation,
    val newPassword : RegisterValidation ,
    val confirmPassword: RegisterValidation
)

data class CarFieldStateOne(
    val name : RegisterValidation,
    val price : RegisterValidation,
    val model : RegisterValidation
)