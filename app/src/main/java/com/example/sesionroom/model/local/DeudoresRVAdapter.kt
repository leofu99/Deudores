package com.example.sesionroom.model.local

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sesionroom.R
import com.example.sesionroom.model.remote.DeudorRemote
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_deudor.view.*

class DeudoresRVAdapter(
    var deudoresList: ArrayList<DeudorRemote>
): RecyclerView.Adapter<DeudoresRVAdapter.DeudoresViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeudoresViewHolder {
        var itemView: View = LayoutInflater.from(parent.context).inflate(R.layout.item_deudor, parent,false)
        return DeudoresViewHolder(itemView)

    }

    override fun getItemCount(): Int = deudoresList.size

    override fun onBindViewHolder(holder: DeudoresViewHolder, position: Int)
    {
        val deudor : DeudorRemote = deudoresList[position]
        holder.bindDeudor(deudor)
    }
    class DeudoresViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        fun bindDeudor(deudor: DeudorRemote){
            itemView.tv_nombre.text = deudor.nombre
            itemView.tv_cantidad.text = deudor.cantidad.toString()
            if(!deudor.urlphoto.isNullOrEmpty())
                Picasso.get().load(deudor.urlphoto).into(itemView.iv_foto)

        }
    }


}