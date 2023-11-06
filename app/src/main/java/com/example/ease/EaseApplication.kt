package com.example.ease

import android.app.Application
import android.content.Intent
import android.util.Log
import com.example.ease.activities.CarsShoppingActivity
import com.example.ease.model.UserModel
import com.example.ease.prefs.SessionPreference

class EaseApplication : Application() {
    companion object{
        lateinit var userRegister : UserModel
        lateinit var session : SessionPreference
    }
    override fun onCreate() {
        super.onCreate()
        userRegister = UserModel()
    }
}