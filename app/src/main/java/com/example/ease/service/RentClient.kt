package com.example.ease.service

import com.example.ease.model.OrderModel
import com.example.ease.model.response.GenericResponse
import com.example.ease.model.response.OrderResponse
import com.example.ease.model.response.OrdersResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface RentClient {
    @GET("rent/{id}")
    suspend fun getOrder( @Path("id")id : String ) : Response< OrderResponse >

    @GET("rent/from/{id}")
    suspend fun getOrdersFrom(@Path("id") id : String) : Response< OrdersResponse >

    @POST("rent/create")
    suspend fun createOrder(@Body orderModel: OrderModel ) : Response< GenericResponse >
}