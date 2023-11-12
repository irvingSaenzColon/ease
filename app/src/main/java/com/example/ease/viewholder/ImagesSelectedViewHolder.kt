package com.example.ease.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.ease.databinding.ImageItemBinding
import com.example.ease.model.ImageSelectedModel


class ImagesSelectedViewHolder(view : View) : RecyclerView.ViewHolder(view) {
    private val binding = ImageItemBinding.bind( view )
    fun render(imageModel: ImageSelectedModel,
               onClickListener : (ImageSelectedModel) -> Unit){
        binding.ivPreviewImage.setImageBitmap( imageModel.bitmap )
        binding.ivPreviewImage.setOnClickListener{ onClickListener( imageModel ) }
    }
}