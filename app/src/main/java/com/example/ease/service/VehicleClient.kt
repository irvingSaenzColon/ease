package com.example.ease.service

import com.example.ease.model.SearchModel
import com.example.ease.model.VehicleModel
import com.example.ease.model.VehicleResponse
import com.example.ease.model.VehiclesResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface VehicleClient {
    @GET("vehicle/{id}")
    suspend fun getVehicle(@Path("id") id : String ) : Response< VehicleResponse >

    @GET("vehicle/belongs/{id}")
    suspend fun getMyVehicles(@Path("id") id :String) : Response< VehiclesResponse >

    @GET("vehicles/latest")
    suspend fun getLatestVehicles() : Response< VehiclesResponse >

    @GET("vehicles/deals")
    suspend fun getDealsVehicles() : Response< VehiclesResponse >

    @GET("vehicles/sellers")
    suspend fun getSellersVehicles() : Response< VehiclesResponse >

    @GET("vehicle/")
    suspend fun getVehicles( ) : Response< VehiclesResponse >

    @POST("vehicles/search")
    suspend fun searchVehicles( @Body searchModel: SearchModel ) : Response< VehiclesResponse >

    @POST("vehicle/create")
    suspend fun createVehicle( @Body vehicleModel: VehicleModel ) : Response< VehicleResponse >

    @PUT("vehicle/update")
    suspend fun updateVehicle( @Body vehicleModel: VehicleModel ) : Response< VehicleResponse >

    @DELETE("vehicle/{id}")
    suspend fun deleteVehicle( @Path("id") id: String ) : Response< VehicleResponse >

}