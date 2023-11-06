package com.example.ease.service

import com.example.ease.model.AuthenticateModel
import com.example.ease.model.UserModel
import com.example.ease.model.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface SignUpClient {
    @POST("signup/")
    suspend fun signup(@Body userModel: UserModel ) : Response<UserResponse>

    @POST("authenticate/")
    suspend fun authenticate(@Body authenticateModel: AuthenticateModel) : Response<UserResponse>
}