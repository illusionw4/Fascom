package com.example.fascom.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fascom.R
import com.example.fascom.currentorderdetail
import com.example.fascom.model.Myordersview
import java.text.SimpleDateFormat
import java.util.*


class Ordersview(private val context: Context, val orderlist: ArrayList<Myordersview>) : RecyclerView.Adapter<Ordersview.MyViewHolder>() {

    var setOnClickListener: (String) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.orderview, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentitem = orderlist[position]
        val lUUID = UUID.randomUUID().toString().replace("-","").substring(0,8)
        val orderDetail: Myordersview = orderlist[position]
        val date: Date = Date(orderDetail.timestamp!!.toLong() * 1000)
        val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy  - HH:mm")

        holder.orderid.text = "ORD" + currentitem.orderId
        holder.placedon.text = "Placed on : " + simpleDateFormat.format(date)
        holder.orderstatus.text = currentitem.status
        holder.ordamt.text = currentitem.totalamount

        holder.btnvi.setOnClickListener {

            var intent = Intent(holder.itemView.context, currentorderdetail::class.java)
                 val ordid: String? = orderlist[position].orderId
                 intent.putExtra("documentid", ordid)
            holder.itemView.context.startActivity(intent)
        }

        if (orderDetail.status == "confirmed") holder.orderstatus.setTextColor(Color.parseColor("#CCA646"))
        else if (orderDetail.status == "Delivered") holder.orderstatus.setTextColor(
            Color.parseColor("#149414")
        )
        else if (orderDetail.status == "cancelled") holder.orderstatus.setTextColor(
            Color.parseColor("#E00201")
        )
        else holder.orderstatus.setTextColor(Color.parseColor("#ac5fe1"))


    }

    override fun getItemCount(): Int {
        return orderlist.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var orderid: TextView = itemView.findViewById(R.id.orderId)
        var placedon: TextView = itemView.findViewById(R.id.placedon)
        var orderstatus: TextView = itemView.findViewById(R.id.orderStatusView)
        var ordamt: TextView = itemView.findViewById(R.id.totalamount)
        var btnvi: TextView = itemView.findViewById(R.id.btnview)


    }
}