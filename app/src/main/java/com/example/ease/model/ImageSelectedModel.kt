package com.example.ease.model

import android.graphics.Bitmap

data class ImageSelectedModel(
    val id: Int,
    val image : String,
    var bitmap : Bitmap? = null
)