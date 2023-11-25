package com.example.ease.viewholder

import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.ease.R
import com.example.ease.databinding.CategoryItemBinding
import com.example.ease.model.CategoryModel

class CategoryViewHolder(view : View) : RecyclerView.ViewHolder( view ) {
    private val binding = CategoryItemBinding.bind( view )

    fun render( categoryModel: CategoryModel, index : Int, onCategorySelect : ( Int ) ->Unit ){
        
        binding.tvCategoryName.text = categoryModel.name
        if(categoryModel.selected)
            binding.tvCategoryName.setTextColor( ContextCompat.getColor( binding.root.context, R.color.electric_indigo ) )
        else
            binding.tvCategoryName.setTextColor( ContextCompat.getColor( binding.root.context, R.color.wolfram ) )

        binding.tvCategoryName.setOnClickListener{ onCategorySelect( index ) }


    }

}