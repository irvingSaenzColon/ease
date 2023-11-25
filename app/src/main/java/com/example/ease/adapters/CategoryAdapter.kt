package com.example.ease.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ease.R
import com.example.ease.model.CategoryModel
import com.example.ease.viewholder.CategoryViewHolder

class CategoryAdapter(
    private val categoryList : List<CategoryModel>,
    private val onSelectCategory : (Int) -> Unit
) : RecyclerView.Adapter<CategoryViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val layoutInflater = LayoutInflater.from( parent.context )
        return CategoryViewHolder( layoutInflater.inflate( R.layout.category_item, parent, false ) )
    }

    override fun getItemCount(): Int = categoryList.size

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val item = categoryList[position]

        holder.render( item, position, onSelectCategory )
    }

}