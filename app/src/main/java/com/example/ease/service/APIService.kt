package com.example.ease.service

import android.util.Log
import com.example.ease.helper.RetrofitHelper
import com.example.ease.model.AuthenticateModel
import com.example.ease.model.UserModel
import com.example.ease.model.UserResponse
import com.example.ease.util.ErrorUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class APIService {
    private val retrofit = RetrofitHelper.getRetrofit("http://192.168.100.9:80/ease/api/")

    suspend fun signup( userModel: UserModel ) : UserResponse{
        return withContext( Dispatchers.IO ){
            val response = retrofit.create( SignUpClient::class.java ).signup( userModel )

            if(!response.isSuccessful) throw Exception( ErrorUtils().parseApiError( response ) )

            response.body() ?:UserResponse()
        }
    }

    suspend fun authenticate( authenticateModel: AuthenticateModel ) : UserResponse{
        return withContext( Dispatchers.IO ){
            val response = retrofit.create( SignInClient::class.java ).authenticate( authenticateModel )

            if(!response.isSuccessful) throw Exception( ErrorUtils().parseApiError( response ) )

            response.body() ?: UserResponse()
        }
    }
}