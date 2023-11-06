package com.example.ease.util


import org.json.JSONObject
import retrofit2.Response

class ErrorUtils {
    fun <T> parseApiError(response : Response< T >) : String{
        val errorMessage = response.errorBody()?.string()?.let{
            JSONObject(it).getString("message")
        } ?: run {
            response.code().toString()
        }

        return errorMessage;
    }
}