package com.example.ease.activities

import android.content.Intent
import com.example.ease.adapters.ViewPagerAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.example.ease.EaseApplication
import com.example.ease.R
import com.example.ease.model.UserModel
import com.example.ease.prefs.SessionPreference

class LoginRegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_register)

        EaseApplication.session = SessionPreference( applicationContext )
        val sessionId = EaseApplication.session.getSessionId()

        Log.i("Session", sessionId.toString())

        if(sessionId >= 0){
            Intent( this,  CarsShoppingActivity::class.java).also { intent ->
                startActivity( intent )
            }
        }

    }

}