package com.example.fascom.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Outline
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.example.fascom.Productactivity
import com.example.fascom.R
import com.example.fascom.model.Fastfood
import com.example.fascom.model.Grocers
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import java.util.HashMap


class FastFooAdap (private val context: Context, val Fastfoolist: ArrayList<Fastfood>, private var firestore: FirebaseFirestore, private var auth: FirebaseAuth) : RecyclerView.Adapter<FastFooAdap.Myfastfood>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Myfastfood {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.fastfooditem, parent, false)
        return Myfastfood(itemView)
    }

    override fun onBindViewHolder(holder: Myfastfood, position: Int) {
        val currentitem = Fastfoolist[position]
        var totalquantity = 1
        var totalprice = 0

        val circularProgressDrawable = CircularProgressDrawable(context)
        circularProgressDrawable.strokeWidth = 8f
//        circularProgressDrawable.colorFilter = ("#ac5fe1")
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.start()

        holder.mmrp.text = currentitem.mrp
        holder.mourpri.text = currentitem.ourpri
        holder.mproductname.text = currentitem.productname
        holder.mquantity.text = currentitem.quantity
        holder.mcost.text = currentitem.cost.toString()
        holder.msize.text = currentitem.size.toString()
        holder.mseller.text = currentitem.seller
        Glide.with(context).load(currentitem.image).placeholder(circularProgressDrawable).into(holder.mimage)
        totalprice = currentitem.cost *totalquantity


        holder.addin.setOnClickListener {
            if (totalquantity < 4) {
                totalquantity++
                totalprice = currentitem.cost *totalquantity
                holder.quan.text = totalquantity.toString()

            }
        }

        holder.removi.setOnClickListener {
            if (totalquantity > 1) {
                totalquantity--
                totalprice = currentitem.cost *totalquantity
                holder.quan.text = totalquantity.toString()
            }
        }


        holder.addbtn.setOnClickListener {
            val intent : Intent? = null
//            totalprice = intent?.getIntExtra("cost",0)!! * totalquantity
            val cartMap = HashMap<String, Any>()
            val image: String = Glide.with(context).load(currentitem.image).into(holder.mimage).toString()
            cartMap["productname"] = currentitem.productname.toString()
            cartMap["price"] = currentitem.ourpri.toString()
            cartMap["cost"] = currentitem.cost.toString()
            cartMap["size"] = currentitem.size.toString()
            cartMap["image"] = currentitem.image.toString()
            cartMap["quant"] = currentitem.quantity.toString()
            cartMap["totalQuantity"] = totalquantity.toString()
            cartMap["totalprice"] = totalprice

            firestore.collection("CurrentUser").document(auth.currentUser!!.uid)
                .collection("FastCart").add(cartMap)
                .addOnCompleteListener(OnCompleteListener<DocumentReference?> {
                    Toast.makeText(context,
                        "Added", Toast.LENGTH_SHORT
                    ).show()
                })
        }

        holder.itemView.setOnClickListener{
            val intent = Intent(holder.itemView.context, Productactivity::class.java)
            intent.putExtra("productname",currentitem.productname)
            intent.putExtra("ourpri",currentitem.ourpri)
            intent.putExtra("cost",currentitem.cost)
            intent.putExtra("size",currentitem.size)
            intent.putExtra("images",currentitem.image)
            intent.putExtra("mrp",currentitem.mrp)
            intent.putExtra("description",currentitem.descr)
            intent.putExtra("stock",currentitem.stock)
            intent.putExtra("quantity",currentitem.quantity)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return Fastfoolist.size
    }

    class Myfastfood(itemView : View): RecyclerView.ViewHolder(itemView){

        var mmrp: TextView = itemView.findViewById(R.id.mrp)
        var mourpri: TextView = itemView.findViewById(R.id.ourpri)
        var mcost: TextView = itemView.findViewById(R.id.cost)
        var msize: TextView = itemView.findViewById(R.id.size)
        var mproductname: TextView = itemView.findViewById(R.id.productname)
        var mquantity: TextView = itemView.findViewById(R.id.quantity)
        var mimage: ImageView = itemView.findViewById(R.id.image)
        var addin: ImageView = itemView.findViewById(R.id.adding)
        var mseller: TextView = itemView.findViewById(R.id.seller)
        var quan: TextView = itemView.findViewById(R.id.quanumber)
        var removi: ImageView = itemView.findViewById(R.id.removing)
        var addbtn: TextView = itemView.findViewById(R.id.addu)
    }
}