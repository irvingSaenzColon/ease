package com.example.ease.helper

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {
    fun getRetrofit(baseURL : String) : Retrofit{
        return Retrofit
            .Builder()
            .baseUrl( baseURL )
            .addConverterFactory( GsonConverterFactory.create() )
            .build()
    }
}