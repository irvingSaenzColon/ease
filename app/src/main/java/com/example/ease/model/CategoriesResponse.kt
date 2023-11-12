package com.example.ease.model

data class CategoriesResponse(
    val body : List<CategoryModel> = arrayListOf(),
    val message : String = ""
)
