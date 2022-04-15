package com.example.fascom.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fascom.Productactivity
import com.example.fascom.R
import com.example.fascom.model.Dotd
import com.example.fascom.model.Explotab

class ExploreAdap (private var doldlist: ArrayList<Explotab>) : RecyclerView.Adapter<ExploreAdap.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.exploitem, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentitem = doldlist[position]
        Glide.with(holder.itemView).load(currentitem.image).placeholder(R.drawable.progressanim).into(holder.mimage)

    }

    override fun getItemCount(): Int {
        return doldlist.size
    }

    class MyViewHolder(itemView : View): RecyclerView.ViewHolder(itemView){

        var mimage: ImageView = itemView.findViewById(R.id.image)
    }
}