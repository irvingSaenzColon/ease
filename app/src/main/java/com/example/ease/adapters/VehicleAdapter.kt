package com.example.ease.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ease.R
import com.example.ease.model.VehicleModel
import com.example.ease.viewholder.VehicleViewHolder

class VehicleAdapter(
    private val vehicleList: List<VehicleModel>,
    private val onClickListener: (VehicleModel) -> Unit
) : RecyclerView.Adapter< VehicleViewHolder >() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VehicleViewHolder {
        val layoutInflater = LayoutInflater.from( parent.context )

        return VehicleViewHolder( layoutInflater.inflate(R.layout.product_rv_item, parent, false) )
    }

    override fun getItemCount(): Int = vehicleList.size

    override fun onBindViewHolder(holder: VehicleViewHolder, position: Int) {
        val item = vehicleList[position]

        holder.render( item, onClickListener )
    }

}