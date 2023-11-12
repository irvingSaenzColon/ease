package com.example.ease.viewholder

import android.graphics.BitmapFactory
import android.util.Base64
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.ease.databinding.ImagePreviewItemBinding
import com.example.ease.model.ImageModel

class ImageViewHolder(view : View) : RecyclerView.ViewHolder(view) {
    private val binding = ImagePreviewItemBinding.bind( view )
    fun render( imageModel: ImageModel ){
        if(imageModel.image != null ){
            val bytes = Base64.decode( imageModel.image, Base64.DEFAULT )
            val bmp = BitmapFactory.decodeByteArray( bytes, 0, bytes.size )
            binding.ivPreview.setImageBitmap( bmp )
        }
    }
}