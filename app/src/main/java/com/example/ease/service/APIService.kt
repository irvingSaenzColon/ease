package com.example.ease.service


import com.example.ease.helper.RetrofitHelper
import com.example.ease.model.AuthenticateModel
import com.example.ease.model.CategoriesResponse
import com.example.ease.model.SearchModel
import com.example.ease.model.SecurityModel
import com.example.ease.model.SecurityResponse
import com.example.ease.model.UserModel
import com.example.ease.model.UserResponse
import com.example.ease.model.VehicleModel
import com.example.ease.model.VehicleResponse
import com.example.ease.model.VehiclesResponse
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

    suspend fun update( userModel: UserModel ) : UserResponse{
        return withContext( Dispatchers.IO ){
            val response = retrofit.create( UserClient::class.java ).update( userModel )

            if(!response.isSuccessful) throw Exception( ErrorUtils().parseApiError( response ) )

            response.body() ?: UserResponse()
        }
    }

    suspend fun changePassword( securityModel: SecurityModel ) : SecurityResponse{
        return withContext( Dispatchers.IO ){
            val response = retrofit.create( SecurityClient::class.java ).changePassword( securityModel )

            if(!response.isSuccessful) throw Exception( ErrorUtils().parseApiError( response ) )

            response.body() ?: SecurityResponse()
        }
    }

    //Category Section

    suspend fun getCategories() : CategoriesResponse{
        return withContext( Dispatchers.IO ){
            val response = retrofit.create( CategoryClient::class.java ).getCategories( )

            if(!response.isSuccessful) throw Exception( ErrorUtils().parseApiError( response ) )

            response.body() ?: CategoriesResponse()
        }
    }

    //Vehicles section

    suspend fun getVehicles() : VehiclesResponse{
        return withContext( Dispatchers.IO ){
            val response = retrofit.create( VehicleClient::class.java ).getVehicles( )

            if(!response.isSuccessful) throw Exception( ErrorUtils().parseApiError( response ) )

            response.body() ?: VehiclesResponse()
        }
    }

    suspend fun getMyVehicles( id :String ) : VehiclesResponse{
        return withContext( Dispatchers.IO ){
            val response = retrofit.create( VehicleClient::class.java ).getMyVehicles( id )

            if(!response.isSuccessful) throw Exception( ErrorUtils().parseApiError( response ) )

            response.body() ?: VehiclesResponse()
        }
    }
    suspend fun getLatestVehicles() : VehiclesResponse{
        return withContext( Dispatchers.IO ){
            val response = retrofit.create( VehicleClient::class.java ).getLatestVehicles( )

            if(!response.isSuccessful) throw Exception( ErrorUtils().parseApiError( response ) )

            response.body() ?: VehiclesResponse()
        }
    }

    suspend fun getDealsVehicles() : VehiclesResponse{
        return withContext( Dispatchers.IO ){
            val response = retrofit.create( VehicleClient::class.java ).getDealsVehicles( )

            if(!response.isSuccessful) throw Exception( ErrorUtils().parseApiError( response ) )

            response.body() ?: VehiclesResponse()
        }
    }

    suspend fun getSellersVehicles() : VehiclesResponse{
        return withContext( Dispatchers.IO ){
            val response = retrofit.create( VehicleClient::class.java ).getSellersVehicles( )

            if(!response.isSuccessful) throw Exception( ErrorUtils().parseApiError( response ) )

            response.body() ?: VehiclesResponse()
        }
    }

    suspend fun getVehicle( id : String ) : VehicleResponse{
        return withContext( Dispatchers.IO ){
            val response = retrofit.create( VehicleClient::class.java ).getVehicle( id )

            if(!response.isSuccessful) throw Exception( ErrorUtils().parseApiError( response ) )

            response.body() ?: VehicleResponse()
        }
    }

    suspend fun searchVehicles( searchModel: SearchModel ) : VehiclesResponse{
        return withContext( Dispatchers.IO ){
            val response = retrofit.create( VehicleClient::class.java ).searchVehicles( searchModel )

            if(!response.isSuccessful) throw Exception( ErrorUtils().parseApiError( response ) )

            response.body() ?: VehiclesResponse()        }
    }

    suspend fun createVehicle( vehicleModel: VehicleModel ) : VehicleResponse{
        return withContext( Dispatchers.IO ){
            val response = retrofit.create( VehicleClient::class.java ).createVehicle( vehicleModel )

            if(!response.isSuccessful) throw Exception( ErrorUtils().parseApiError( response ) )

            response.body() ?: VehicleResponse()
        }
    }

    suspend fun updateVehicle( vehicleModel: VehicleModel ) : VehicleResponse{
        return withContext( Dispatchers.IO ){
            val response = retrofit.create( VehicleClient::class.java ).updateVehicle( vehicleModel )

            if(!response.isSuccessful) throw Exception( ErrorUtils().parseApiError( response ) )

            response.body() ?: VehicleResponse()
        }
    }

    suspend fun deleteVehicle( id: String ) : VehicleResponse{
        return withContext( Dispatchers.IO ){
            val response = retrofit.create( VehicleClient::class.java ).deleteVehicle( id )

            if(!response.isSuccessful) throw Exception( ErrorUtils().parseApiError( response ) )

            response.body() ?: VehicleResponse()
        }
    }
}