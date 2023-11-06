package com.example.ease.network

import com.example.ease.model.UserModel
import com.example.ease.model.UserResponse
import com.example.ease.service.APIService

class SignUpRepository  {
    private val api = APIService()

    suspend fun signup( userModel : UserModel ) : UserResponse{
        return api.signup( userModel )
    }
}