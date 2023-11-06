package com.example.ease.service


import com.example.ease.model.AuthenticateModel
import com.example.ease.model.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface SignInClient {
    @POST("authenticate/")
    suspend fun authenticate(@Body authenticateModel: AuthenticateModel) : Response<UserResponse>
}