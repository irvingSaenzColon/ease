package com.example.ease.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ease.R
import com.example.ease.model.ImageModel
import com.example.ease.model.ImageSelectedModel
import com.example.ease.viewholder.ImagesSelectedViewHolder

class ImagesSelectedAdapter(
    private val imagesSelectedList : List<ImageSelectedModel>,
    private val onClickListener : (ImageSelectedModel) -> Unit
) : RecyclerView.Adapter<ImagesSelectedViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImagesSelectedViewHolder {
        val layoutInflater = LayoutInflater.from( parent.context )

        return ImagesSelectedViewHolder( layoutInflater.inflate( R.layout.image_item, parent, false ) )
    }

    override fun getItemCount(): Int = imagesSelectedList.size

    override fun onBindViewHolder(holder: ImagesSelectedViewHolder, position: Int) {
        val item = imagesSelectedList[position]
        holder.render( item, onClickListener )
    }
}