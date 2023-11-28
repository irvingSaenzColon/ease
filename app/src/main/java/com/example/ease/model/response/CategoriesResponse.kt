package com.example.ease.model.response

import com.example.ease.model.CategoryModel

data class CategoriesResponse(
    val body : List<CategoryModel> = arrayListOf(),
    val message : String = ""
)
