package com.example.ease.prefs

import android.content.Context
import com.example.ease.model.UserModel

class SessionPreference (context : Context){

    private val SHARED_NAME = "session"
    private val SHARED_ID ="id"
    private val SHARED_USERNAME = "username"
    private val SHARED_NICKNAME = "nickname"
    private val SHARED_BIRTHDATE = "birthdate"
    private val SHARED_GENDER = "gender"
    private val SHARED_EMAIL ="email"
    private val SHARED_PASSWORD = "password"
    private val SHARED_CONFIRMPASSWORD = "confirmpassword"
    private val SHARED_PICTURE = "picture"

    private val storage = context.getSharedPreferences( SHARED_NAME, 0 )

    fun getSessionId() : Int = storage.getInt( SHARED_ID, -1 )

    fun storeSession(userModel: UserModel){
        storage.edit().putInt(SHARED_ID, userModel.id).apply()
        storage.edit().putString(SHARED_USERNAME, userModel.name).apply()
        storage.edit().putString(SHARED_NICKNAME, userModel.nickname).apply()
        storage.edit().putString(SHARED_BIRTHDATE, userModel.birthdate).apply()
        storage.edit().putString(SHARED_GENDER, userModel.gender).apply()
        storage.edit().putString(SHARED_EMAIL, userModel.email).apply()
        storage.edit().putString(SHARED_PICTURE, userModel.picture).apply()
        storage.edit().putString(SHARED_PASSWORD, userModel.password).apply()

    }

    fun getSession() : UserModel{
        return UserModel(
            storage.getInt( SHARED_ID,  -1),
            storage.getString( SHARED_USERNAME, "" )!!,
            storage.getString( SHARED_NICKNAME, "" )!!,
            storage.getString( SHARED_BIRTHDATE, "" )!!,
            storage.getString( SHARED_GENDER, "" )!!,
            storage.getString( SHARED_EMAIL, "" )!!,
            storage.getString( SHARED_PASSWORD, "" )!!,
            "",
            storage.getString( SHARED_PICTURE, "" )!!
            )
    }



    fun clearSession(){
        storage.edit().putInt( SHARED_ID, -1).apply()
        storage.edit().putString(SHARED_USERNAME, "").apply()
        storage.edit().putString(SHARED_NICKNAME, "").apply()
        storage.edit().putString(SHARED_BIRTHDATE, "").apply()
        storage.edit().putString(SHARED_GENDER, "").apply()
        storage.edit().putString(SHARED_EMAIL, "").apply()
        storage.edit().putString(SHARED_PASSWORD, "").apply()
        storage.edit().putString(SHARED_CONFIRMPASSWORD, "").apply()
        storage.edit().putString(SHARED_PICTURE, "").apply()
    }
}