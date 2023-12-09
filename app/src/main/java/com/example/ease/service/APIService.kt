package com.example.ease.service


import com.example.ease.helper.RetrofitHelper
import com.example.ease.model.AuthenticateModel
import com.example.ease.model.FavoriteModel
import com.example.ease.model.OrderModel
import com.example.ease.model.PaymentModel
import com.example.ease.model.response.CategoriesResponse
import com.example.ease.model.SearchModel
import com.example.ease.model.SecurityModel
import com.example.ease.model.SecurityResponse
import com.example.ease.model.UserModel
import com.example.ease.model.UserResponse
import com.example.ease.model.VehicleModel
import com.example.ease.model.VehicleResponse
import com.example.ease.model.VehiclesResponse
import com.example.ease.model.response.GenericResponse
import com.example.ease.model.response.OrderResponse
import com.example.ease.model.response.OrdersResponse
import com.example.ease.model.response.PaymentResponse
import com.example.ease.model.response.PaymentsResponse
import com.example.ease.util.ErrorUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class APIService {
    // Development server http://192.168.100.9:80/ease/api/
    // Cloud Sever https://easebk.000webhostapp.com/api/
    private val retrofit = RetrofitHelper.getRetrofit("https://easebk.000webhostapp.com/api/")

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

    suspend fun getCategories() : CategoriesResponse {
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

    //Payments

    suspend fun getPayment( id : String ) : PaymentResponse{
        return withContext( Dispatchers.IO ){
            val response = retrofit.create( PaymentClient::class.java ).getPayment( id )

            if(!response.isSuccessful) throw Exception( ErrorUtils().parseApiError( response ) )

            response.body() ?: PaymentResponse()
        }
    }

    suspend fun getAllMyPayments( id : String ) : PaymentsResponse{
        return withContext( Dispatchers.IO ){
            val response = retrofit.create( PaymentClient::class.java ).getMyPayments( id )

            if(!response.isSuccessful) throw Exception( ErrorUtils().parseApiError( response ) )

            response.body() ?: PaymentsResponse()
        }
    }
    suspend fun  createPayment( paymentModel: PaymentModel ) : PaymentResponse{
        return withContext( Dispatchers.IO ){
            val response = retrofit.create( PaymentClient::class.java ).createPayment( paymentModel )

            if(!response.isSuccessful) throw Exception( ErrorUtils().parseApiError( response ) )

            response.body() ?: PaymentResponse()
        }
    }

    suspend fun updatePayment( paymentModel: PaymentModel ) : PaymentResponse{
        return withContext( Dispatchers.IO ){
            val response = retrofit.create( PaymentClient::class.java ).updatePayment( paymentModel )

            if(!response.isSuccessful) throw Exception( ErrorUtils().parseApiError( response ) )

            response.body() ?: PaymentResponse()
        }
    }

    suspend fun deletePayment( id: String ) : PaymentResponse{
        return withContext( Dispatchers.IO ){
            val response = retrofit.create( PaymentClient::class.java ).deletePayment( id )

            if(!response.isSuccessful) throw Exception( ErrorUtils().parseApiError( response ) )

            response.body() ?: PaymentResponse()
        }
    }

    //Favorites

    suspend fun getFavoritesFrom( id: String ) : VehiclesResponse{
        return withContext( Dispatchers.IO ){
            val response = retrofit.create( FavoriteClient::class.java ).getFavoriteFrom( id )

            if(!response.isSuccessful) throw Exception( ErrorUtils().parseApiError( response ) )

            response.body() ?: VehiclesResponse()
        }
    }

    suspend fun getSpecificFavorite( favoriteModel: FavoriteModel ) : VehicleResponse {
        return withContext( Dispatchers.IO ){
            val response = retrofit.create( FavoriteClient::class.java ).getSpecificFavorite( favoriteModel )

            if(!response.isSuccessful) throw Exception( ErrorUtils().parseApiError( response ) )

            response.body() ?: VehicleResponse()
        }
    }

    suspend fun markCar( favoriteModel: FavoriteModel ) : GenericResponse{
        return withContext( Dispatchers.IO ){
            val response = retrofit.create( FavoriteClient::class.java ).markFavoriteCar( favoriteModel )

            if(!response.isSuccessful) throw Exception( ErrorUtils().parseApiError( response ) )

            response.body() ?: GenericResponse()
        }
    }

    //Orders
    suspend fun getOrder( id : String ) : OrderResponse{
        return withContext( Dispatchers.IO ){
            val response = retrofit.create( RentClient::class.java ).getOrder( id )

            if(!response.isSuccessful) throw Exception( ErrorUtils().parseApiError( response ) )

            response.body() ?: OrderResponse()
        }
    }

    suspend fun getOrdersFrom( id : String ): OrdersResponse{
        return withContext( Dispatchers.IO ){
            val response = retrofit.create( RentClient::class.java ).getOrdersFrom( id )

            if(!response.isSuccessful) throw Exception( ErrorUtils().parseApiError( response ) )

            response.body() ?: OrdersResponse()
        }
    }

    suspend fun createOrder( orderModel: OrderModel ) : GenericResponse{
        return withContext( Dispatchers.IO ){
            val response = retrofit.create( RentClient::class.java ).createOrder( orderModel )

            if(!response.isSuccessful) throw Exception( ErrorUtils().parseApiError( response ) )

            response.body() ?: GenericResponse()
        }
    }
}