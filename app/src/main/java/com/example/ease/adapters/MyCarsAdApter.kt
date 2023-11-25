package com.example.ease.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ease.R
import com.example.ease.model.VehicleModel
import com.example.ease.viewholder.MyCarsViewHolder

class MyCarsAdApter(
    private val myCarsList : List<VehicleModel>,
    private val onOpenPopUp :(View, Int, VehicleModel) -> Unit
) : RecyclerView.Adapter<MyCarsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyCarsViewHolder {
        val layoutInflater = LayoutInflater.from( parent.context )

        return MyCarsViewHolder( layoutInflater.inflate( R.layout.my_car_item,  parent,false ) )
    }

    override fun getItemCount(): Int = myCarsList.size

    override fun onBindViewHolder(holder: MyCarsViewHolder, position: Int) {
        val item = myCarsList[position]

        holder.render( item, position, onOpenPopUp )
    }
}