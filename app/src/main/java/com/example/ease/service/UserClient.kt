package com.example.ease.service

import com.example.ease.model.UserModel
import com.example.ease.model.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserClient {
    @POST("user/update")
    suspend fun update(@Body userModel: UserModel) : Response< UserResponse >
}