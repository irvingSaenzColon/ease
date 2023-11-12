package com.example.ease.service

import com.example.ease.model.SecurityModel
import com.example.ease.model.SecurityResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface SecurityClient {
    @POST("security/password")
    suspend fun changePassword( @Body securityModel: SecurityModel ) : Response< SecurityResponse >
}