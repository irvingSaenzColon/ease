package com.example.ease.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ease.R
import com.example.ease.model.ImageModel
import com.example.ease.viewholder.ImageViewHolder

class ImageAdapter(
    private val imagesList : List<ImageModel>
) : RecyclerView.Adapter<ImageViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val layoutInflater = LayoutInflater.from( parent.context )
        return ImageViewHolder( layoutInflater.inflate( R.layout.image_preview_item, parent, false ) )
    }

    override fun getItemCount(): Int = imagesList.size

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val item = imagesList[position]

        holder.render( item )
    }
}