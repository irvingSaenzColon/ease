package com.example.ease.service

import com.example.ease.model.PaymentModel
import com.example.ease.model.response.PaymentResponse
import com.example.ease.model.response.PaymentsResponse
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.Response
import retrofit2.http.Body

interface PaymentClient {

    @GET("payment/{id}")
    suspend fun getPayment(@Path("id") id : String ) : Response < PaymentResponse >

    @GET("payment/from/{id}")
    suspend fun getMyPayments(@Path("id") id: String) : Response < PaymentsResponse >

    @POST("payment/create")
    suspend fun createPayment(@Body paymentModel: PaymentModel ) : Response<PaymentResponse>

    @POST("payment/update")
    suspend fun updatePayment(@Body paymentModel: PaymentModel) : Response< PaymentResponse >

    @GET("payment/delete/{id}")
    suspend fun deletePayment(@Path("id") id : String) : Response < PaymentResponse >
}