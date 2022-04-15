package com.example.fascom.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.example.fascom.R
import com.example.fascom.model.MyCartModel

class Ordersinsideview(private val context: Context, private var orderinsidelist: ArrayList<MyCartModel>) : RecyclerView.Adapter<Ordersinsideview.Myordersinside>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Myordersinside {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.orderitemlist, parent, false)
        return Myordersinside(itemView)
    }

    override fun onBindViewHolder(holder: Myordersinside, position: Int) {
        val currentitem = orderinsidelist[position]

        holder.mproductname.text = currentitem.productname
        holder.mcost.text = currentitem.cost
        holder.mtotalquantity.text = currentitem.totalQuantity
        holder.mquant.text = currentitem.quant
        holder.msize.text = currentitem.size
        holder.mprogress.visibility = View.GONE
    }

    override fun getItemCount(): Int {
        return orderinsidelist.size
    }

    class Myordersinside(itemView : View): RecyclerView.ViewHolder(itemView) {

        var mquant: TextView = itemView.findViewById(R.id.quant)
        var mprice: TextView = itemView.findViewById(R.id.price)
        var mproductname: TextView = itemView.findViewById(R.id.product_name)
        var mtotalquantity: TextView = itemView.findViewById(R.id.totalQuantity)
        var mcost: TextView = itemView.findViewById(R.id.cost)
        var msize: TextView = itemView.findViewById(R.id.size)
        var mprogress : ProgressBar = itemView.findViewById(R.id.progress)

    }

}