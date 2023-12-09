package com.example.ease.service

import com.example.ease.model.FavoriteModel
import com.example.ease.model.VehicleResponse
import com.example.ease.model.VehiclesResponse
import com.example.ease.model.response.GenericResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface FavoriteClient {

    @GET("favorite/from/{id}")
    suspend fun getFavoriteFrom(@Path("id") id: String) : Response< VehiclesResponse >
    @POST("favorite/user")
    suspend fun getSpecificFavorite(@Body favoriteModel: FavoriteModel) : Response < VehicleResponse >
    @POST("favorite/mark")
    suspend fun markFavoriteCar(@Body favoriteModel: FavoriteModel) : Response< GenericResponse >

}