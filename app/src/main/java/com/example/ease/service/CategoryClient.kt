package com.example.ease.service

import com.example.ease.model.CategoriesResponse
import retrofit2.Response
import retrofit2.http.GET

interface CategoryClient {
    @GET("category/")
    suspend fun getCategories() : Response< CategoriesResponse >
}